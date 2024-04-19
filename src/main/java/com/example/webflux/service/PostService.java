package com.example.webflux.service;

import com.example.webflux.client.PostClient;
import com.example.webflux.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostClient postClient;

    public Mono<PostResponse> getPost(Long id) {
        return postClient.getPost(id)
                .onErrorResume(error -> Mono.just(new PostResponse(id.toString(), "FallBack Data %d".formatted(id))))
                ;
    }

    public Flux<PostResponse> getPosts(List<Long> idList) {
        return Flux.fromIterable(idList)
                .flatMap(this::getPost);
    }

    public Flux<PostResponse> getParallelPosts(List<Long> idList) {
        return Flux.fromIterable(idList)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(this::getPost)
                .log()
                .sequential();
    }
}
