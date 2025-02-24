package com.zhl.springboot3demo1.chapter02Reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

//public class createDemo {
//    public static void main(String[] args) throws IOException {
//        new createDemo().create();
////        System.in.read();
//    }
//
//    public void create(){
//        Flux.create(fluxSink->{
//            MyListener myListener = new MyListener(fluxSink);
//            for (int i = 0; i < 100000000; i++) {
//                myListener.online("张"+i);
//            }
//        })
//                .log()
//                .subscribe();
//
//    }
//
//    class MyListener{
//        FluxSink<Object> sink;
//
//        public MyListener(FluxSink<Object> sink) {
//            this.sink = sink;
//        }
//
//        public void online(String username){
//            System.out.println("用户"+username+"上线了");
//        }
//    }
//}
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class createDemo {
    public static void main(String[] args) throws IOException {
        new createDemo().create();
//        System.in.read(); // 防止主线程退出
    }

    public void create() {
        ExecutorService executor = Executors.newFixedThreadPool(10); // 使用多线程线程池

        Flux.create(fluxSink -> {
                    MyListener myListener = new MyListener(fluxSink);
                    for (int i = 0; i < 10000; i++) {
                        int finalI = i;
                        executor.submit(() -> myListener.online("张" + finalI)); // 异步调用
                    }
                })
                .log()
                .subscribe();

        executor.shutdown(); // 关闭线程池
    }

    class MyListener {
        FluxSink<Object> sink;

        public MyListener(FluxSink<Object> sink) {
            this.sink = sink;
        }

        public void online(String username) {
            System.out.println("用户" + username + "上线了");
            sink.next(username); // 发射数据
        }
    }
}
