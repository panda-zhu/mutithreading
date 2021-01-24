package com.panda.mutithreading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    private static final int CORE_POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        te.setCorePoolSize(CORE_POOL_SIZE);
        te.setMaxPoolSize(MAX_POOL_SIZE);
        te.setQueueCapacity(QUEUE_CAPACITY);
        te.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        te.setThreadNamePrefix("MyThreadPollTaskExecutor");
        te.initialize();
        return te;
    }
}
