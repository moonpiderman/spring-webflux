package com.example.webflux.controller;

import com.example.webflux.dto.PostResponse;
import com.example.webflux.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/{id}")
    public Mono<PostResponse> getPost(@PathVariable Long id) throws InterruptedException {
        return postService.getPost(id);
    }

    @GetMapping("/search")
    public Flux<PostResponse> getPosts(
            @RequestParam(name = "ids") List<Long> idList
    ) {
        return postService.getParallelPosts(idList);
    }
}
