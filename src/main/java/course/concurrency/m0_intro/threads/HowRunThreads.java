package course.concurrency.m0_intro.threads;


import lombok.SneakyThrows;

public class HowRunThreads {
    public static void main(String[] args) throws InterruptedException {
        Runnable task1 = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Task 1: " + i);
            }
        };

        Runnable task2 = () -> {
            for (int i = 10; i <= 15; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Task 2: " + i);
            }
        };

        var thread1 = new Thread(task1);
        thread1.setName("My Thread 1");
        // Создать фоновый поток:
        thread1.setDaemon(true);

        var thread2 = new Thread(task2);
        thread2.setName("My Thread 2");

        thread1.start();
        thread2.start();

        System.out.println(Thread.currentThread().getName());
        /**
         * При вызове t.run() просто выполнится код внутри метода без создания нового потока.
         * Чтобы код выполнился в новом потоке, нужно вызвать t.start().
         * Под капотом будет создан новый поток в операционной системе и множество структур внутри JVM.
         * И затем в новом потоке выполнится метод run().
         *
         * Существуют обычные и фоновые потоки.
         */
        // ----------------------------------------------------------------- //

        Runnable task3 = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Task 3: " + i);
            }
        };

        Runnable task4 = () -> {
            for (int i = 10; i <= 15; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Task 4: " + i);
            }
        };

        var thread3 = new Thread(task3);
        thread3.setName("My Thread 3");

        var thread4 = new Thread(task4);
        thread4.setName("My Thread 4");

        thread3.start();
        thread3.join();
        System.out.println(thread3.getName() + " is Done");
        thread4.start();
        System.out.println(thread4.getName() + " is Working");

        /**
         * Если код не обрабатывает прерывания, бесполезно использовать метод interrupt
         */

    }
}
