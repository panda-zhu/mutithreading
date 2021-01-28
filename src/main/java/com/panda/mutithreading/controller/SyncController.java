package com.panda.mutithreading.controller;

import com.panda.mutithreading.service.SyncService;
import com.panda.mutithreading.vo.Demo2RequestVo;
import com.panda.mutithreading.vo.Demo2ResponseVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
@Api
public class SyncController {
    @Autowired
    private SyncService syncService;

    @Value("${string.url}")
    private String url;

    @PostMapping("/alarm/list")
    public String getTeamByIndex(String arg){
        long start = System.currentTimeMillis();
        System.out.println("请求参数的json为："+arg);
        List<String> words = Arrays.asList("f", "i","l","w");
        List<CompletableFuture<List<String>>> collect = words.stream().map(word -> syncService.completableFutureTask(word)).collect(Collectors.toList());
        List<List<String>> collect1 = collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println("总共花费时间"+(System.currentTimeMillis()-start)/1000+"秒");
        return collect1.toString();
    }

    @GetMapping("/list")
    public List<Demo2ResponseVo> getDemo2List(Demo2RequestVo demo2RequestVo){
        return syncService.getDemo2List(demo2RequestVo);
    }

    @GetMapping("/rest")
    public String getRestTempData(){
        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8080/sync/team";
        System.out.println(url);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.getBody();
    }
}
