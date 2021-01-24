package com.panda.mutithreading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MutithreadingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutithreadingApplication.class, args);
    }

}
