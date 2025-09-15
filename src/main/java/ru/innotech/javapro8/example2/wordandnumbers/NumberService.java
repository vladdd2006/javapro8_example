package ru.innotech.javapro8.example2.wordandnumbers;

import java.util.Comparator;
import java.util.List;

public class NumberService {

  private static final String ERROR_SIZE = "Размерность массива меньше 3";

  public static void main(String[] args) {
    System.out.println("------------- Run task 1 (Max third number) ------------");
    List<Integer> arrayInt1 = List.of(1, 2, 10, 23, 20, 10, 40, 3, 4, 6);
    System.out.println("ArrayList : " + arrayInt1);
    Integer result1 = getMaxThirdItem(arrayInt1);
    System.out.println("Third max number: " + result1);

    System.out.println("------------- Run task 2 (Max third uniq number)------------");
    List<Integer> arrayInt2 = List.of(1, 2, 10, 23, 20, 10, 10, 3, 23, 23, 4, 6);
    System.out.println("ArrayList : " + arrayInt2);
    Integer result2 = getMaxThirdUniqItem(arrayInt2);
    System.out.println("Third max uniq number: " + result2);
  }

  /**
   * Найдите в списке целых чисел 3-е наибольшее число
   *
   * @param arrayInt - массив чисел
   */
  private static Integer getMaxThirdItem(List<Integer> arrayInt) {
    if (arrayInt == null || arrayInt.size() < 3) {
      throw new IllegalArgumentException(ERROR_SIZE); // так как не будет третьего по порядку
    }
    return arrayInt.stream()
        .sorted(Comparator.reverseOrder())
        .skip(2)
        .findFirst()
        .get();
  }

  /**
   * Найдите в списке целых чисел 3-е наибольшее «уникальное» число
   *
   * @param arrayInt - массив чисел
   */
  private static Integer getMaxThirdUniqItem(List<Integer> arrayInt) {
    if (arrayInt == null || arrayInt.size() < 3) {
      throw new IllegalArgumentException(ERROR_SIZE); // так как не будет третьего по порядку
    }

    return arrayInt.stream()
        .sorted(Comparator.reverseOrder())
        .distinct()
        .skip(2)
        .findFirst()
        .get();
  }

}
