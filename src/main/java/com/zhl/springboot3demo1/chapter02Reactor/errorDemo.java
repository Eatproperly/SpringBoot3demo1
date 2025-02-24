package com.zhl.springboot3demo1.chapter02Reactor;


import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

public class errorDemo {

    @Test
    /**
     * onErrorReturn
     */
    public void onErrorReturn(){
        Flux.just(1,2,3,0,4)
                .map(i->"100/"+i+"="+100/i)
                .onErrorReturn(ArithmeticException.class,"六百六十六")
                .subscribe(v-> System.out.println("v = " + v),
                        err-> System.out.println("err = " + err),
                        ()-> System.out.println("流结束"));
    }
    @Test
    /**
     * onErrorResume
     */
    public void onErrorResume(){
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
//                .onErrorResume(err -> Mono.just("哈哈-777"))
//                .onErrorResume(err -> hhh(err))
//                .onErrorResume(err -> Flux.error(new BusinessException(err.getMessage()+"：炸了")))
//                .onErrorMap(err-> new BusinessException(err.getMessage()+": 又炸了..."))
//                .doOnError(err -> {
//                    System.out.println("err已被记录 = " + err);
//                })
//                .doFinally(signalType -> {
//                    System.out.println("流信号："+signalType);
//                })
                .onErrorContinue((err,val)->{
                    System.out.println("err = " + err);
                    System.out.println("val = " + val);
                    System.out.println("发现"+val+"有问题了，继续执行其他的，我会记录这个问题");
                }) //发生
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }
    Mono<String> hhh(Throwable throwable){
        if(throwable.getClass()==ArithmeticException.class){
            //一些处理
        }
        return Mono.just("hhh"+throwable.getMessage());
    }
    class BusinessException extends RuntimeException{
        public BusinessException() {
        }
        public BusinessException(String message) {
            super(message);
        }

        public BusinessException(String message, Throwable cause) {
            super(message, cause);
        }

        public BusinessException(Throwable cause) {
            super(cause);
        }

        public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
    /**
     * HotFlux
     */
    @Test
    public void testHotFlux() throws InterruptedException, IOException {
        ConnectableFlux<Integer> hotFlux = Flux.interval(Duration.ofSeconds(1))
                .map(i -> i.intValue() + 1)
                .take(5)
                .publish(); // 转换为热流

    // 手动启动数据生产（不依赖订阅者）
            hotFlux.connect();

            hotFlux.subscribe(v-> System.out.println("v = " + v));
    // 延迟订阅
            Thread.sleep(3000);
            hotFlux.subscribe(i -> System.out.println("订阅者: " + i));
    // 输出：订阅者: 4、5（错过1,2,3）
        System.in.read();
//        v = 1
//        v = 2
//        v = 3
//        v = 4
//        订阅者: 4
//        v = 5
//        订阅者: 5
    }
}
