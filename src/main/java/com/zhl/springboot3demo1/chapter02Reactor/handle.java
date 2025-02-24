package com.zhl.springboot3demo1.chapter02Reactor;

import reactor.core.publisher.Flux;

public class handle {
    public static void main(String[] args) {
        //自定义流中处理规则
        Flux.range(1,10)
                .handle((value,sink)->{
                    System.out.println("拿到的值"+value);
                    sink.next("张三"+value);
                })
                .log()
                .subscribe();
    }

}
