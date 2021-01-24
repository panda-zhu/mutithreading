package com.panda.mutithreading.methodtest;

import com.panda.mutithreading.task.Demo1Task;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class SynchornizedTest implements Runnable{
    private int count = 10;
    @Override
    public  void run() {
        count--;
        System.out.println(Thread.currentThread().getName()+" count="+count);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        for (int i = 0; i < 100; i++) {
//            new Thread(new SynchornizedTest(),"Thread"+i).start();
//        }

//        CompletableFuture.supplyAsync(()->1).thenApply(i->i+2).thenApply(i->i*3).whenComplete((r,e)-> System.out.println(r));

//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        executorService.submit(new Demo1Task());
            //测试map
//        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
//        List<Integer> squaresList = numbers.stream().map( i -> {
//            if(i ==2){
//                i+=500;
//            }
//            return i;
//        }).collect(Collectors.toList());
//        squaresList.stream().forEach(System.out::println);

        CompletableFuture<List<String>> future1 = CompletableFuture.supplyAsync(()->{
            try {
                System.out.println("开始调用数据中台的接口");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Arrays.asList("aaa","bbb","ccc");
        });
//        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(()->{
//            try {
//                System.out.println("开始调用业务接口");
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "future2";
//        });
//        System.out.println(CompletableFuture.allOf(future1,future2).get());
//        System.out.println(CompletableFuture.anyOf(future1,future2).get());
//        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(future1, future2);
        future1.whenComplete((s,e)->{
            try {
                s.stream().forEach(System.out::println);
                System.out.println("开始调用业务接口");
                Thread.sleep(2000);
            } catch (InterruptedException ee) {
                ee.printStackTrace();
            }
        });
    }



}
