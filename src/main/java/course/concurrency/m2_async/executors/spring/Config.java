package course.concurrency.m2_async.executors.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class Config {

    @Bean
    public ThreadPoolTaskExecutor applicationTaskExecutor() {
        var threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(4);
        threadPoolTaskExecutor.setQueueCapacity(2);
        threadPoolTaskExecutor.setThreadNamePrefix("AsynchThread1::");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Bean
    public ThreadPoolTaskExecutor applicationTaskExecutor2() {
        var threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(4);
        threadPoolTaskExecutor.setQueueCapacity(2);
        threadPoolTaskExecutor.setThreadNamePrefix("AsynchThread2::");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
