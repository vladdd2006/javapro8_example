package ru.innotech.javapro8.example2.wordandnumbers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordApp {

  public static void main(String[] args) {
    List<String> stringList = initArrayWord();
    System.out.println("Words : " + stringList);
    System.out.println("------------- Run task 5 (Find max length word) ------------");
    System.out.println("Max length word: " + getMaxLengthWord(stringList));

    System.out.println("------------- Run task 6 (Get Map words) ------------");
    var stringValue = "map stream api list array list make easy stream collect collection list";
    System.out.println("Map words: " + getWordMap(stringValue));

    System.out.println("------------- Run task 7 (Print sorted word list) ------------");
    printWords(stringList);

    System.out.println("------------- Run task 8 (Find max length word from list words) ------------");
    List<String> stringList2 = initArrayWords();
    System.out.println("List words : " + stringList2);
    String maxLengthWord = getMaxLengthWord2(stringList2);
    System.out.println("Max length word: " + maxLengthWord);
  }

  /**
   * Найдите в списке слов самое длинное
   *
   * @param words - массив слов
   * @return самое длинное слово
   */
  private static String getMaxLengthWord(List<String> words) {
    return words.stream()
        .max(Comparator.comparingInt(String::length))
        .orElse("");
  }

  /**
   * Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно
   * встречается во входной строке
   */
  private static Map<String, Long> getWordMap(String value) {
    String[] splitStr = value.split(" ");
    return Arrays.stream(splitStr)
        .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
  }

  /**
   * Отпечатайте в консоль строки из списка в порядке увеличения длины слова, если слова имеют одинаковую длины, то должен быть сохранен алфавитный
   * порядок
   *
   * @param words - массив слов
   */
  private static void printWords(List<String> words) {
    words.stream().sorted(Comparator.comparingInt(String::length).thenComparing(word -> word))
        .forEach(System.out::println);
  }

  /**
   * Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом, найдите среди всех слов самое длинное, если таких слов
   * несколько, получите любое из них
   */
  private static String getMaxLengthWord2(List<String> word) {
    return word.stream()
        .map(a -> Arrays.stream(a.split(" ")).toList())
        .flatMap(List::stream)
        .max(Comparator.comparingInt(String::length))
        .orElse("");
  }

  /**
   * Инициализация массива слов
   *
   * @return массив слов
   */
  private static List<String> initArrayWord() {
    return List.of(
        "дом",
        "книга",
        "язык",
        "солнце",
        "машина",
        "путешествие",
        "восьмиэтажный",
        "садоводство",
        "электростанция",
        "достопримечательность",
        "звёздный",
        "кофе",
        "программирование",
        "ист",
        "международный",
        "небо",
        "философия",
        "яблоко",
        "экран"
    );
  }

  private static List<String> initArrayWords() {
    return List.of(
        "красный яблоко стол быстро бежать",
        "солнце утро кофе книга тишина",
        "город машина люди шум ночь",
        "река вода рыба лес птицы",
        "дом семья тепло уют вечер",
        "школа ученик учитель урок знание",
        "зима снег холод санки праздник",
        "лето жара море пляж отдых",
        "осень листья дождь зонт настроение",
        "весна автоматизация солнце радость пробуждение",
        "компьютер программа данные интернет технология",
        "музыка звук инструмент мелодия противостояние",
        "спорт движение здоровье энергия победа",
        "еда вкус аромат саморазрушение наслаждение",
        "путешествие дорога приключение открытие впечатление"
    );
  }

}
