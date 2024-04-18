package com.example.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SamplerController {

    @GetMapping("/mono-hello")
    public Mono<String> getHello() {

        // 아래는 publisher만 존재하고 subscriber는 없다.
        return Mono.just("Hello Rest Controller With Webflux!!");
    }
}
