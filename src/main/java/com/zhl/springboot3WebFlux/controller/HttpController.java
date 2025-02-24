package com.zhl.springboot3WebFlux.controller;

import org.junit.runners.Parameterized;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.DispatcherHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.Style;
import java.awt.*;
import java.time.Duration;


@RestController
public class HttpController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "key",required = false,defaultValue = "哈哈")String key){
        return "Hello , key"+key;
    }

    /**
     * 现在推荐的方式：
     * 返回单个数据：Mono<Order>,User,String,map
     * 返回多个数据：Flux<Order>
     * 配合Flux, Server Send Event 完成服务端事件推送
     * @return
     */
    @GetMapping("/haha")
    public Mono<String> haha(){
        return Mono.just("haha");
    }
    @GetMapping("/hehe")
    public Flux<String> hehe(){
        return Flux.just("hehe1","hehehe");
    }
    @GetMapping(value = "/sse" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> sse(){
        return Flux.range(1,10)
//                .map(i->i+" hehe ")
                .map(i->{
                    return ServerSentEvent.builder("ha-"+i)
                            .id(i+" ")
                            .comment("xi")
                            .data("hei"+i)
                            .event("hhh")
                            .build();
                })
                .delayElements(Duration.ofMillis(500));
    }

}
