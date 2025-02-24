package com.zhl.springboot3demo1.chapter02Reactor;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

public class generateDemo {
    public static void main(String[] args) throws IOException {
        new generateDemo().test();

//        System.in.read();
    }
    public static void mainaaa(String[] args) {
        Flux<Object> flux = Flux.generate(() -> 0, (state, sink) -> {
            sink.next(state + 2);
            if (state == 10) sink.complete();
            return state + 1;
        });
        flux.subscribe(System.out::println);
    }

    public void test(){
        Flux.range(1,100)
                .delayElements(Duration.ofSeconds(1))
                .log()
                .subscribe(System.out::println);
    }
    public void dispose(){
        Flux<Integer> flux = Flux.range(1, 10000)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> i + 3)
                .log();
        Disposable disposable = flux.subscribe(System.out::println);

        new Thread(()->{
            try {
                Thread.sleep(10000);
                disposable.dispose();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
