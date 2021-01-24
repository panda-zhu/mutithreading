package com.panda.mutithreading.task;

import java.util.Random;
import java.util.concurrent.Callable;

public class Demo1Task implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "sucess";
    }
}
