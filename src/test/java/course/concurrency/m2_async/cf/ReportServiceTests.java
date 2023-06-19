package course.concurrency.m2_async.cf;

import course.concurrency.m2_async.cf.report.ReportServiceCF;
import course.concurrency.m2_async.cf.report.ReportServiceExecutors;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ReportServiceTests {

    private ReportServiceExecutors reportService = new ReportServiceExecutors();
//    private ReportServiceCF reportService = new ReportServiceCF();

    @Test
    public void testMultipleTasks() throws InterruptedException {
        int poolSize = Runtime.getRuntime().availableProcessors()*50;
        int iterations = 5;

        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        System.out.println("poolSize: " + poolSize);
        for (int i = 0; i < poolSize; i++) {
            executor.submit(() -> {
                try {
                    latch.await();
                } catch (InterruptedException ignored) {}
                for (int it = 0; it < iterations; it++) {
                    reportService.getReport();
                }
            });
        }

        long start = System.currentTimeMillis();
        latch.countDown();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);
        long end = System.currentTimeMillis();

        System.out.println("Execution time: " + (end - start));
    }
}

/**
 * ѕроц 8 €дер ћ1
 *
 * 10 задач + newFixedThreadPool(1):
 * sleep+compute: 156546
 *
 * 10 задач + newFixedThreadPool(2):
 * sleep+compute: 78357
 *
 * 10 задач + newFixedThreadPool(4):
 * sleep+compute: 47410
 *
 * 10 задач + newFixedThreadPool(8):
 * sleep+compute: 31573
 *
 * 10 задач + newFixedThreadPool(16):
 * sleep+compute: 16263
 *
 * 10 задач + newFixedThreadPool(32):
 * sleep+compute: 16237
 *
 * 10 задач + newCachedThreadPool():
 * sleep+compute: 16009
 *
 * 8 задач + newFixedThreadPool(1):
 * sleep+compute: 156546
 *
 * 8 задач + newFixedThreadPool(2):
 * sleep+compute: 62851
 *
 * 8 задач + newFixedThreadPool(4):
 * sleep+compute: 31667
 *
 * 8 задач + newFixedThreadPool(8):
 * sleep+compute: 16100
 *
 * 8 задач + newFixedThreadPool(16):
 * sleep+compute: 15985
 *
 * 8 задач + newFixedThreadPool(32):
 * sleep+compute: 16018
 *
 * 8 задач + newCachedThreadPool():
 * sleep+compute: 16141
 *
 * 24 задач + newFixedThreadPool(8):
 * sleep+compute: 47366
 *
 * 24 задач + newFixedThreadPool(16):
 * sleep+compute: 32212
 *
 * 24 задач + newFixedThreadPool(32):
 * sleep+compute: 17270
 *
 * 100 задач + newFixedThreadPool(100):
 * sleep+compute: 27797
 *
 * 400 задач + newFixedThreadPool(400):
 * sleep+compute: 92264
 *
 * 400 задач + newFixedThreadPool(400):
 * sleep: 15118
 */
