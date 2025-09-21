package ru.innotech.javapro8.example3.threads;

public class CustomExecutorApp {

  public static final Integer COUNT_POOL_SIZE = 3; // размер пула для запуска потоков
  public static final Integer COUNT_THREADS = 20; // количество потоков

  public static void main(String[] args) {
    CustomExecutor executor = new CustomExecutor(COUNT_POOL_SIZE);

    for (int i = 0; i < COUNT_THREADS; i++) {
      int taskId = i;
      executor.execute(() -> {
        System.out.println(Thread.currentThread().getName() + ": выполняется задача " + taskId);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
      });
    }

    executor.shutdown();
    executor.awaitTermination();
    System.out.println("Все задачи выполнены.");
  }

}
