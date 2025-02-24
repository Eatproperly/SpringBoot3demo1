package com.zhl.springboot3demo1.chapter02Reactor;

import org.springframework.scheduling.annotation.Schedules;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class threadDemo {
    public static void main(String[] args) {
//        new threadDemo().test();
        new threadDemo().thread1();
    }
    public void test(){
        Scheduler single = Schedulers.newSingle("single-scheduler");
//        Scheduler boundedSchedule = Schedulers.newBoundedElastic(4, 8, "boundedSchedule");
        Flux.range(1,10)
                .log()
                .publishOn(single)
                .delayElements(Duration.ofSeconds(1))
                .log()
//                .subscribeOn(boundedSchedule)
//                .log()
                .subscribe();

    }
    public void thread1(){
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i)
                .log()
                .publishOn(s)
                .map(i -> "value " + i)
                .log()
                ;

        //只要不指定线程池，默认发布者用的线程就是订阅者的线程；
        new Thread(() -> flux.subscribe(System.out::println)).start();
    }
}
