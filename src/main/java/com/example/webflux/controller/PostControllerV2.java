package com.example.webflux.controller;

import com.example.webflux.dto.PostCreateRequest;
import com.example.webflux.dto.PostResponseV2;
import com.example.webflux.repository.Post;
import com.example.webflux.service.PostServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts/v2")
@RequiredArgsConstructor
public class PostControllerV2 {
    private final PostServiceV2 postServiceV2;

    @PostMapping("")
    public Mono<PostResponseV2> createPost(
            @RequestBody PostCreateRequest postCreateRequest
    ) {
        Mono<Post> post = postServiceV2.createPost(
                postCreateRequest.getUserId(),
                postCreateRequest.getTitle(),
                postCreateRequest.getContent()
        );

        return post.map(PostResponseV2::of);
    }

    @GetMapping("")
    public Flux<PostResponseV2> findAllPosts() {
        return postServiceV2.findAll()
                .map(PostResponseV2::of);
    }

    @GetMapping("/{id}")
    public Mono<PostResponseV2> findPostById(
            @PathVariable Long id
    ) {
        return postServiceV2.findById(id)
                .map(PostResponseV2::of);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePost(
            @PathVariable Long id
    ) {
        return postServiceV2.deletePost(id);
    }
}
