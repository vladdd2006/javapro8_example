package ru.innotech.javapro8.example2.employeers;

public class Employer {

  // имя
  private String name;
  // возраст
  private int age;
  // должность
  private PostEnum post;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public PostEnum getPost() {
    return post;
  }

  public void setPost(PostEnum post) {
    this.post = post;
  }

  public Employer(String name, int age, PostEnum post) {
    this.name = name;
    this.age = age;
    this.post = post;
  }

  @Override
  public String toString() {
    return "Employer{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", post='" + post + '\'' +
        '}';
  }

  public enum PostEnum {
    DIRECTOR,
    ANALYTIC,
    ENGINEER,
    DEVELOPER
  }
}
