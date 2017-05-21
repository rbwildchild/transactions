package com.rfa.n26.transactions.config;

import com.rfa.n26.transactions.task.StatsTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableAsync
public class TaskConfiguration {

    @Bean
    public StatsTask asyncTask() {
        return new StatsTask(asyncExecutor());
    }

    @Bean
    public TaskExecutor asyncExecutor() {
        System.out.println("Hi there");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(2);
        executor.setThreadPriority(Thread.MIN_PRIORITY);
        executor.initialize();
        return executor;
    }

}
