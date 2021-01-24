package com.panda.mutithreading.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SyncService {
    private static final Logger logger = LoggerFactory.getLogger(SyncService.class);
    private List<String> teams = new ArrayList<String>(Arrays.asList("rng","edg","fpx","ig","v5","we","lgd"));

    @Async
    public CompletableFuture<List<String>> completableFutureTask(String start){
        logger.warn(Thread.currentThread().getName()+"start execute this task");
        List<String> results = teams.stream().filter(team->team.startsWith(start)).collect(Collectors.toList());
        //模拟这个方法需要2s才能执行完。
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(results);
    }


}
