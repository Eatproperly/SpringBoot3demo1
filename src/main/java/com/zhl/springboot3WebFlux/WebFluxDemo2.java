package com.zhl.springboot3WebFlux;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import reactor.core.publisher.Mono;

import reactor.netty.http.server.HttpServer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class WebFluxDemo2 {

    public static void main(String[] args) throws IOException {
        HttpHandler handler = new HttpHandler() {
            @Override
            public Mono<Void> handle(ServerHttpRequest request, ServerHttpResponse response) {
                System.out.println(Thread.currentThread());
                System.out.println(request.getURI());
                DataBufferFactory factory = response.bufferFactory();
                DataBuffer buffer = factory.wrap(new String(request.getURI().toString() + "Hello!").getBytes(StandardCharsets.UTF_8));
//                return Mono.empty();
                return response.writeWith(Mono.just(buffer));

            }
        };
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(adapter)
                .bindNow();

        System.in.read();
    }
}