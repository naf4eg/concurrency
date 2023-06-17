package course.concurrency.m0_intro.threads;

import java.util.concurrent.*;

public class HowWorkWithExecutors {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        workWithExecutors();
        workWithFeatureAndExecutors();
    }

    private static void workWithExecutors() {
        System.out.println(Thread.currentThread().getName());
        var executorService = Executors.newCachedThreadPool();
        Runnable task = () -> {
            int result = 12*15;
            System.out.println(Thread.currentThread().getName() + " " + result);
        };
        executorService.submit(task);
    }

    private static void workWithFeatureAndExecutors() throws ExecutionException, InterruptedException {
        var executorService = Executors.newCachedThreadPool();
        Callable<Integer> task = () -> 12*10;
        var result = executorService.submit(task);
        while (!result.isDone()) {
            System.out.println("working....");
        }
        var value = result.get();
        System.out.println(Thread.currentThread().getName() + " | " + "Result Callable: " + value);
    }
}
