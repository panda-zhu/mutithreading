package com.panda.mutithreading.controller;

import com.panda.mutithreading.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SyncController {
    @Autowired
    private SyncService syncService;

    @GetMapping("/team")
    public String getTeamByIndex(){
        long start = System.currentTimeMillis();
        List<String> words = Arrays.asList("f", "i","l","w");
        List<CompletableFuture<List<String>>> collect = words.stream().map(word -> syncService.completableFutureTask(word)).collect(Collectors.toList());
        List<List<String>> collect1 = collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println("总共花费时间"+(System.currentTimeMillis()-start)/1000+"秒");
        return collect1.toString();
    }
}
