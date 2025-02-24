package com.zhl.springboot3WebFlux.controller;

import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class helloWebFlux {

    // 1. 基本 GET 请求
    @GetMapping("/hello")
    public Mono<String> hello() {
        System.out.println("hello");
        System.out.println("baibai");
        System.out.println("merge-test1");
        System.out.println("master");
        System.out.println("hotfix");
        return Mono.just("Hello WebFlux!");
    }

    // 2. 路径变量
    @GetMapping("/user/{id}")
    public Mono<Map<String, Object>> getUser(@PathVariable String id) {
        return Mono.just(Map.of(
                "id", id,
                "name", "User" + id,
                "createdAt", Instant.now()
        ));
    }

    // 3. 请求参数
    @GetMapping("/search")
    public Flux<Integer> search(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int size) {
        return Flux.range((page-1)*size, 10);
    }

    // 4. 请求头处理
    @GetMapping("/header")
    public Mono<String> getHeader(@RequestHeader("User-Agent") String userAgent) {
        return Mono.just("Your User-Agent: " + userAgent);
    }

    // 5. 表单提交
    @PostMapping("/form")
    public Mono<Map<String, Object>> handleForm(
            @RequestParam String username,
            @RequestParam String email) {
        return Mono.just(Map.of(
                "username", username,
                "email", email,
                "status", "created"
        ));
    }

    // 6. JSON 请求体
    @PostMapping("/json")
    public Mono<Map<String, Object>> handleJson(@RequestBody Mono<Map<String, Object>> body) {
        return body.map(data -> {
            data.put("processed", true);
            data.put("timestamp", Instant.now());
            return data;
        });
    }

    // 7. 文件上传
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> handleUpload(@RequestPart("file") FilePart filePart) {
        return filePart.content()
                .map(data -> {
                    // 这里简单演示获取文件名
                    return "Received file: " + filePart.filename();
                })
                .next();
    }

    // 8. 异常处理演示
    @GetMapping("/error-demo")
    public Mono<String> errorDemo() {
        return Mono.error(new IllegalArgumentException("Test error"));
    }

    // 9. SSE (Server-Sent Events)
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String, Object>> streamData() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> Map.of(
                        "value", i,
                        "timestamp", Instant.now()
                ));
    }

    // 10. 延迟响应
    @GetMapping("/delay")
    public Mono<String> delayedResponse() {
        return Mono.just("Delayed Response")
                .delayElement(Duration.ofSeconds(3));
    }
}



