package ru.innotech.javapro8.example1.dto;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.innotech.javapro8.example1.annotation.AfterSuite;
import ru.innotech.javapro8.example1.annotation.AfterTest;
import ru.innotech.javapro8.example1.annotation.BeforeSuite;
import ru.innotech.javapro8.example1.annotation.BeforeTest;
import ru.innotech.javapro8.example1.annotation.CsvSource;
import ru.innotech.javapro8.example1.annotation.Size;
import ru.innotech.javapro8.example1.annotation.Test;

public class TestRunner {

  private static final String PRIORITY = "priority";
  private static final String NO_MORE_THEN_ONE = "Не больше одной аннотации";
  private static final String ONE_OR_MORE = "Хотя бы одна аннотация";

  public static void runTests(Class zClass) {
    try {
      Method[] declaredMethods = zClass.getDeclaredMethods();

      //Происходит проверка количества аннотаций
      Map<String, List<Method>> mapMethods = checkAllAnnotations(declaredMethods);

      // Создаем экземпляр тестового класса
      Constructor<People> constructor = zClass.getConstructor(String.class, int.class);
      People peopleTest = constructor.newInstance("Иванов Иван Иванович", 30);

      var beforeTestMethods = mapMethods.get(BeforeTest.class.getSimpleName());
      for (Method method : beforeTestMethods) {
        method.invoke(peopleTest);
      }

      try {
        //Выполняется метод с аннотацией @BeforeSuite, если такой есть
        var beforeSuiteMethod = mapMethods.get(BeforeSuite.class.getSimpleName()).get(0);
        if (beforeSuiteMethod != null) {
          beforeSuiteMethod.invoke(peopleTest);
        }

        // Остортирем методы с аннотацией Test по приоритету
        var testMethods = mapMethods.get(Test.class.getSimpleName());
        List<Method> prioritylist = testMethods.stream().sorted((a, b) -> {
          Test annotationA = a.getAnnotation(Test.class);
          Test annotationB = b.getAnnotation(Test.class);
          return annotationA.priority() - annotationB.priority();
        }).toList();

        for (Method method : prioritylist) {
          if (method.isAnnotationPresent(CsvSource.class)) {
            String value = method.getAnnotation(CsvSource.class).value();
            String[] args = value.split(",");
            method.invoke(peopleTest, args[0], Integer.valueOf(args[1].trim()));
          } else {
            method.invoke(peopleTest);
          }
        }

      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        //Выполняется метод с аннотацией @AfterSuite, если такой есть
        var afterSuiteMethod = mapMethods.get(AfterSuite.class.getSimpleName()).get(0);
        if (afterSuiteMethod != null) {
          afterSuiteMethod.invoke(peopleTest);
        }
        var afterTestMethods = mapMethods.get(AfterTest.class.getSimpleName());
        for (Method method : afterTestMethods) {
          method.invoke(peopleTest);
        }
      }

      System.out.println("FINISH running test: " + zClass.getName());
    } catch (Exception e) {
      System.out.println("FAILED running test: " + zClass.getName() + ": " + e.getMessage());
      throw new IllegalStateException(e);
    }
  }

  /**
   * Проверка количества аннотаций annotationName в методах класса
   *
   * @param declaredMethods - методы класса
   * @return список методов по группам BeforeSuite, AfterSuite, Test, BeforeTest, AfterTest
   */
  private static Map<String, List<Method>> checkAllAnnotations(Method[] declaredMethods) throws NoSuchMethodException {
    Map<String, List<Method>> allMethods = new HashMap<>();

    List<Method> beforeSuiteMethods = new ArrayList<>();
    List<Method> afterSuiteMethods = new ArrayList<>();
    List<Method> testMethods = new ArrayList<>();
    List<Method> beforeTestMethods = new ArrayList<>();
    List<Method> afterTestMethods = new ArrayList<>();

    int countBefore = 0;
    int countAfter = 0;
    int countTest = 0;
    for (Method method : declaredMethods) {
      if (method.isAnnotationPresent(BeforeSuite.class)) {
        countBefore++;
        beforeSuiteMethods.add(method);
      }
      if (method.isAnnotationPresent(AfterSuite.class)) {
        countAfter++;
        afterSuiteMethods.add(method);
      }
      if (method.isAnnotationPresent(Test.class)) {
        // проверка значения priority
        checkPriorityValue(method);
        countTest++;
        testMethods.add(method);
      }
      if (method.isAnnotationPresent(BeforeTest.class)) {
        beforeTestMethods.add(method);
      }
      if (method.isAnnotationPresent(AfterTest.class)) {
        afterTestMethods.add(method);
      }
    }
    //Происходит проверка, что методов с аннотациями @BeforeSuite не больше одного
    if (countBefore > 1) {
      throw new IllegalStateException("Аннотация @BeforeSuite не удовлетворяет условию: " + NO_MORE_THEN_ONE);
    }
    //Происходит проверка, что методов с аннотациями @AgetrSuite не больше одного
    if (countAfter > 1) {
      throw new IllegalStateException("Аннотация @AfterSuite не удовлетворяет условию: " + NO_MORE_THEN_ONE);
    }
    //Происходит проверка, что есть хотя бы один метод с аннотацией @Test
    if (countTest < 1) {
      throw new IllegalStateException("Аннотация @Test не удовлетворяет условию: " + ONE_OR_MORE);
    }

    allMethods.put(BeforeSuite.class.getSimpleName(), beforeSuiteMethods);
    allMethods.put(AfterSuite.class.getSimpleName(), afterSuiteMethods);
    allMethods.put(Test.class.getSimpleName(), testMethods);
    allMethods.put(BeforeTest.class.getSimpleName(), beforeTestMethods);
    allMethods.put(AfterTest.class.getSimpleName(), afterTestMethods);

    return allMethods;
  }

  /**
   * Проверка значения поля priority в @Test
   *
   * @param method - аннотированный метод
   * @throws NoSuchMethodException - исключение при отсутствии метода
   */
  private static void checkPriorityValue(Method method) throws NoSuchMethodException {
    Test annotation = method.getAnnotation(Test.class);
    Method methodPriority = annotation.annotationType().getMethod(PRIORITY);
    Size annotationSize = methodPriority.getDeclaredAnnotation(Size.class);
    if (annotation.priority() < annotationSize.min() || annotation.priority() > annotationSize.max()) {
      throw new IllegalStateException("Параметр priority не удовлетворяет условию");
    }
  }
}
