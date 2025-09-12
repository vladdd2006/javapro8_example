package ru.innotech.javapro8.example2.employeers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmployerApp {

  public static void main(String[] args) {
    System.out.println("------------- Run task 3 (Employer max third age) ------------");
    List<Employer> employers = initEmployers();
    System.out.println("Employers : " + employers);
    List<Employer> engineers = getThirdEngineers(employers);
    System.out.println("---- Result Engineers: -----");
    engineers.forEach(System.out::println);

    System.out.println("------------- Run task 3 (Employer max third age) ------------");
    Double averageAge = getAverageAge(employers);
    System.out.printf("Result average age: %.1f%n", averageAge);
  }

  /**
   * Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3 самых старших сотрудников с должностью
   * «Инженер», в порядке убывания возраста
   *
   * @param employers - список сотрудников
   */
  private static List<Employer> getThirdEngineers(List<Employer> employers) {
    return employers.stream()
        .filter(x -> x.getPost().equals(Employer.PostEnum.ENGINEER))
        .sorted(Comparator.comparingInt(Employer::getAge).reversed())
        .limit(3)
        .toList();
  }

  /**
   * Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников с должностью «Инженер»
   *
   * @param employers список сотрудников
   * @return - средний возраст Инженеров
   */
  private static Double getAverageAge(List<Employer> employers) {
    return employers.stream()
        .filter(x -> x.getPost().equals(Employer.PostEnum.ENGINEER))
        .mapToInt(Employer::getAge)
        .average().orElse(0);
  }

  /**
   * Инициализация массива сотрудников
   *
   * @return - список сотрудников
   */
  public static List<Employer> initEmployers() {
    List<Employer> arrayInt = new ArrayList<>();

    arrayInt.add(new Employer("Иванов Иван Иванович", 50, Employer.PostEnum.DIRECTOR));
    arrayInt.add(new Employer("Петров Петр Васильевич", 44, Employer.PostEnum.ANALYTIC));
    arrayInt.add(new Employer("Попов Виктор Петрович", 40, Employer.PostEnum.ENGINEER));
    arrayInt.add(new Employer("Серов Сергей Иванович", 50, Employer.PostEnum.ENGINEER));
    arrayInt.add(new Employer("Иванов Сергей Петров", 30, Employer.PostEnum.ENGINEER));
    arrayInt.add(new Employer("Сергеев Иван Попович", 35, Employer.PostEnum.ENGINEER));
    arrayInt.add(new Employer("Сергеев Иван Попович", 18, Employer.PostEnum.ENGINEER));
    arrayInt.add(new Employer("Баранов Виктор Семенович", 20, Employer.PostEnum.DEVELOPER));

    return arrayInt;
  }

}
