package com.example.webflux.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SampleHandler {

    public Mono<ServerResponse> getString(ServerRequest request) {
        return ServerResponse.ok().body(Mono.just("Hello, Spring!"), String.class);
    }
}
