package course.concurrency.m2_async.executors_practice;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * количество потоков выделяется не сразу, а по мере необходимости
 */
public class ExecutorsPractice {
    public static long longTask() throws InterruptedException {
        Thread.sleep(1000); // + try-catch
        return ThreadLocalRandom.current().nextInt();
    }

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> longTask());
            System.out.print(executor.getPoolSize() + " ");
            System.out.println("=> " + executor.getQueue().remainingCapacity());
        }

        executor.shutdown();
    }
}
