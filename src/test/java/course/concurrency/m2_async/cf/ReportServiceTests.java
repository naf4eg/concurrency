package course.concurrency.m2_async.cf;

import course.concurrency.m2_async.cf.report.ReportServiceCF;
import course.concurrency.m2_async.cf.report.ReportServiceExecutors;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ReportServiceTests {

   //private ReportServiceExecutors reportService = new ReportServiceExecutors();
   private ReportServiceCF reportService = new ReportServiceCF();

    @Test
    public void testMultipleTasks() throws InterruptedException {
        int poolSize = Runtime.getRuntime().availableProcessors();
        int iterations = 1;

        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        System.out.println("poolSize: " + poolSize);
        for (int i = 0; i <10; i++) {
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
 * CF
 *
 * compute:
 * task: 32
 * Execution time: 3082
 *
 * task: 64
 * Execution time: 5896
 *
 * task: 80
 * Execution time: 9706
 *
 * task: 120
 * Execution time: 11584
 *
 * task: 400
 * Execution time: 41766
 *
 *
 *
 * sleep:
 * poolSize: 400
 * не дождался
 *
 *
 * sleep + compute:
 * 1 task
 * Execution time: 15652
 *
 * 8 task
 * Execution time: 28365
 *
 *
 */

/**
 * ���� 8 ���� �1
 *
 * 10 ����� + newFixedThreadPool(1):
 * sleep+compute: 156546
 *
 * 10 ����� + newFixedThreadPool(2):
 * sleep+compute: 78357
 *
 * 10 ����� + newFixedThreadPool(4):
 * sleep+compute: 47410
 *
 * 10 ����� + newFixedThreadPool(8):
 * sleep+compute: 31573
 *
 * 10 ����� + newFixedThreadPool(16):
 * sleep+compute: 16263
 *
 * 10 ����� + newFixedThreadPool(32):
 * sleep+compute: 16237
 *
 * 10 ����� + newCachedThreadPool():
 * sleep+compute: 16009
 *
 * 8 ����� + newFixedThreadPool(1):
 * sleep+compute: 156546
 *
 * 8 ����� + newFixedThreadPool(2):
 * sleep+compute: 62851
 *
 * 8 ����� + newFixedThreadPool(4):
 * sleep+compute: 31667
 *
 * 8 ����� + newFixedThreadPool(8):
 * sleep+compute: 16100
 *
 * 8 ����� + newFixedThreadPool(16):
 * sleep+compute: 15985
 *
 * 8 ����� + newFixedThreadPool(32):
 * sleep+compute: 16018
 *
 * 8 ����� + newCachedThreadPool():
 * sleep+compute: 16141
 *
 * 24 ����� + newFixedThreadPool(8):
 * sleep+compute: 47366
 *
 * 24 ����� + newFixedThreadPool(16):
 * sleep+compute: 32212
 *
 * 24 ����� + newFixedThreadPool(32):
 * sleep+compute: 17270
 *
 * 100 ����� + newFixedThreadPool(100):
 * sleep+compute: 27797
 *
 * 400 ����� + newFixedThreadPool(400):
 * sleep+compute: 92264
 *
 * 400 ����� + newFixedThreadPool(400):
 * sleep: 15118
 */
