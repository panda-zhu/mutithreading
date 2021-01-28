package com.panda.mutithreading.service;

import com.panda.mutithreading.vo.Demo2RequestVo;
import com.panda.mutithreading.vo.Demo2ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
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

    /**
     * 1.先从一个特别耗时的任务中获取一个list
     * 2.根据这个list结果字段，再在另外两个耗时任务中继续查询数据。
     * 3.将三个耗时任务返回结果拼接成一个list返回。
     */
    public List<Demo2ResponseVo> getDemo2List(Demo2RequestVo demo2RequestVo){
        List<Demo2ResponseVo> demo2ResponseVos = this.getNameList(demo2RequestVo);
        long startTime = System.currentTimeMillis();
        CompletableFuture<List<Integer>> age = CompletableFuture.supplyAsync(() -> {
            System.out.println("==========getAge开始执行！");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("+++++++++++getAge结束执行！");
            return Arrays.asList(3, 4, 5, 6, 7, 8, 9);
        });
        CompletableFuture<Void> voidCompletableFuture1 = age.thenAccept(list -> {
            for (int i = 0; i < list.size(); i++) {
                demo2ResponseVos.get(i).setAge(list.get(i));
            }
        });

        CompletableFuture<List<String>> sex = CompletableFuture.supplyAsync(()->{
            System.out.println("==========getSex开始执行！");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("+++++++++++getSex结束执行！");
            return Arrays.asList("nan", "nv", "nan", "nan", "nv", "nan", "nv");
        });
        CompletableFuture<Void> voidCompletableFuture = sex.thenAccept(list -> {
            for (int i = 0; i < list.size(); i++) {
                demo2ResponseVos.get(i).setSex(list.get(i));
            }
        });
        try {
            CompletableFuture.allOf(voidCompletableFuture1,voidCompletableFuture).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return demo2ResponseVos;
    }

    private List<Demo2ResponseVo> getNameList(Demo2RequestVo demo2RequestVo){
        List<String> p = new ArrayList<String>(Arrays.asList("zhangsan","lisi","wangwu","zhaoliu","fangqi","nanba","qijiu"));
        List<Demo2ResponseVo> collect = p.stream().filter(str->{
            if(!StringUtils.isEmpty(demo2RequestVo.getName())){
                str.equals(demo2RequestVo.getName());
            }
            return true;
        }).map(str -> {
            Demo2ResponseVo demo2ResponseVo = new Demo2ResponseVo();
            demo2ResponseVo.setName(str);
            return demo2ResponseVo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Async
    public CompletableFuture<List<Integer>> getAge(){
        System.out.println("==========getAge开始执行！");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(Arrays.asList(3, 4, 5, 6, 7, 8, 9));
    }

    @Async
    public CompletableFuture<List<String>> getSex(){
        System.out.println("==========getSex开始执行！");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(Arrays.asList("nan", "nv", "nan", "nan", "nan", "nan", "nan"));
    }
}
