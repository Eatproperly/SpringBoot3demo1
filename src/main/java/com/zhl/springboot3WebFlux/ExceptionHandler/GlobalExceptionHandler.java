package com.zhl.springboot3WebFlux.ExceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

// 全局异常处理
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Mono<Map<String, Object>>> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest()
                .body(Mono.just(Map.of(
                        "error", ex.getMessage(),
                        "status", 400
                )));
    }
}
