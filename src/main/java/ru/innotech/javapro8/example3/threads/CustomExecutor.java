package ru.innotech.javapro8.example3.threads;

import java.util.LinkedList;
import java.util.List;

public class CustomExecutor {

  private final int poolSize;
  private final List<MyWorker> myWorkers;
  private final LinkedList<Runnable> taskQueue = new LinkedList<>();
  private volatile boolean isShutdown = false;

  public CustomExecutor(int poolSize) {
    this.poolSize = poolSize;
    this.myWorkers = new LinkedList<>();

    initWorkers();
  }

  /**
   * Инициализация потоков в количестве poolSize
   */
  private void initWorkers() {
    for (int i = 0; i < this.poolSize; i++) {
      MyWorker myWorker = new MyWorker("MyWorker-" + i);
      myWorkers.add(myWorker);
      myWorker.start();
    }
  }

  /**
   * Добавляет задачу в очередь на выполнение
   */
  public void execute(Runnable task) {
    synchronized (taskQueue) {
      if (isShutdown) {
        throw new IllegalStateException("Executor уже завершен");
      }
      System.out.println("Задача добавлена в список");
      taskQueue.add(task);
      taskQueue.notify();
    }
  }

  public void shutdown() {
    synchronized (taskQueue) {
      isShutdown = true;
      taskQueue.notifyAll();
    }
  }

  public void awaitTermination() {
    for (MyWorker w : myWorkers) {
      try {
        w.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException("awaitTermination interrupted", e);
      }
    }
  }

  private class MyWorker extends Thread {

    public MyWorker(String name) {
      super(name);
    }

    @Override
    public void run() {
      while (true) {
        Runnable task;
        synchronized (taskQueue) {
          while (taskQueue.isEmpty() && !isShutdown) {
            try {
              taskQueue.wait();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              return;
            }
          }

          if (taskQueue.isEmpty() && isShutdown) {
            break;
          }

          task = taskQueue.removeFirst();
        }

        try {
          task.run();
        } catch (RuntimeException e) {
          System.err.println("Задача " + getName() + " выполнена с ошибкой: " + e.getMessage());
        }
      }
    }
  }
}
