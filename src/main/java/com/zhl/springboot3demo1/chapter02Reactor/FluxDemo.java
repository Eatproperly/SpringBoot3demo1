package com.zhl.springboot3demo1.chapter02Reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.io.IOException;
import java.time.Duration;

public class FluxDemo {
    public static void main(String[] args) {
        Flux.range(1,1000)
                .log()
                .limitRate(100)
                .subscribe();
        Flux.range(1,10)
                .buffer(3)
                .log()
                .subscribe();
    }
    public static void FluxDoOnXxx(String[] args) {
        Flux.just(1,2,3,4,5,6,0,5,4,3,2,1)
                .map(integer -> 10/integer)
                .doOnNext(s-> System.out.println("数据到达 " + s))
                .doOnError(throwable ->  System.out.println("数据异常已经被处理"+throwable.getMessage()))
                .subscribe(System.out::println);
    }
    public static void fluxDoOn(String[] args) throws IOException, InterruptedException {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6, 7)
//                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(()->{
            System.out.println("流正常结束");
        })
                .doOnCancel(()->{
                    System.out.println("流正常取消");
                })
                .doOnError(throwable -> {
                    System.out.println("流出错"+throwable);
                })
                .doOnNext(s-> System.out.println("元素到达..."+s))
                ;

//        flux.subscribe(s-> System.out.println("s = " + s));
        flux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("订阅完成。。"+subscription);
                request(1);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("数据到达:"+value);
                if (value<4) {
                    if(value==3){int i=1/0;}
                    request(1);
                }
                else cancel();
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("数据流结束");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("数据流异常"+throwable);
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("订阅被取消。。");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("结束信号"+type);
            }
        });
//        System.in.read();
        Thread.sleep(2000);
    }
}
