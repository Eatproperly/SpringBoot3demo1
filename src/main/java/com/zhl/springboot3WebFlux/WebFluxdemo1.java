package com.zhl.springboot3WebFlux;


//import org.springframework.http.server.reactive.HttpHandler;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
//import reactor.core.publisher.Mono;
//import reactor.netty.http.server.HttpServer;
//import java.io.IOException;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

public class WebFluxdemo1 {
    private static HttpHandler handler;

    public static void main(String[] args) throws IOException {


        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer.create()
                .host("localhost").
                port(8080).
                handle(adapter).
                bindNow();

        System.in.read();
    }
}
