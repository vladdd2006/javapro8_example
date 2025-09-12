package ru.innotech.javapro8.example1.dto;

import ru.innotech.javapro8.example1.annotation.AfterSuite;
import ru.innotech.javapro8.example1.annotation.AfterTest;
import ru.innotech.javapro8.example1.annotation.BeforeSuite;
import ru.innotech.javapro8.example1.annotation.BeforeTest;
import ru.innotech.javapro8.example1.annotation.CsvSource;
import ru.innotech.javapro8.example1.annotation.Test;

public class People {

  private String fio;
  private int age;

  public People(String fio, int age) {
    this.fio = fio;
    this.age = age;
  }

  @BeforeSuite
  public static void init() {
    System.out.println("Started methods People");
  }

// метод для проверки количества BeforeSuite
//  @BeforeSuite
//  public static void init2() {
//    System.out.println("Started");
//  }

  @AfterSuite
  public static void finish() {
    System.out.println("Finished methods People");
  }

  public String getFio() {
    System.out.println("Run test with priority 2");
    return fio;
  }

  public void setFio(String fio) {
    this.fio = fio;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Test(priority = 6)
  public void printFio() {
    System.out.println("Run test with priority 6 - People FIO: " + fio);
  }

  @Test(priority = 7)
  public void printAge() {
    System.out.println("Run test with priority 7 - People Age: " + age);
  }

  @Test
  @BeforeTest
  public void printFullInfo() {
    System.out.println("Run test with priority default(5) - People FullInfo: " + fio + ", " + age);
  }

  @Test(priority = 10)
  @AfterTest
  public void printLastPriority() {
    System.out.println("Run test with priority 10 - " + this);
  }

  /**
   * Метод изменения ФИО и возраста
   */
  @Test(priority = 9)
  @CsvSource("Петров Иван Васильевич, 40")
  public void changeFioAndAge(String fio, int age) {
    System.out.println("Run test with priority 9 - Change FIO and Age");
    setFio(fio);
    setAge(age);
    System.out.println(this);
  }

  @Override
  public String toString() {
    return "People {" +
        "fio='" + fio + '\'' +
        ", age=" + age +
        '}';
  }
}
