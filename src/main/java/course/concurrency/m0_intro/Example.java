package course.concurrency.m0_intro;

public class Example {
    public static void main(String[] args) {
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
        var thread2 = new Thread(task2);

        //При вызове t.run() просто выполнится код внутри метода без создания нового потока.
        //
        //Чтобы код выполнился в новом потоке, нужно вызвать t.start(). Под капотом будет создан новый поток в операционной системе и множество структур внутри JVM.
        //
        //И затем в новом потоке выполнится метод run
        thread1.start();
        thread2.start();
    }
}
