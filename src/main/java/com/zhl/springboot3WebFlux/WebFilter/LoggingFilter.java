package com.zhl.springboot3WebFlux.WebFilter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

// 添加一个简单的 WebFilter
@Component
public class LoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("Request to: " + exchange.getRequest().getPath());
        return chain.filter(exchange);
    }
}
