package ru.innotech.javapro8.example1;

import ru.innotech.javapro8.example1.dto.People;
import ru.innotech.javapro8.example1.dto.TestRunner;

public class MainApp {

  public static void main(String[] args) {
    TestRunner.runTests(People.class);
  }
}
