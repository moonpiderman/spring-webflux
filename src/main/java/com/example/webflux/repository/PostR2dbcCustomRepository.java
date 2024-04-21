package com.example.webflux.repository;

import reactor.core.publisher.Flux;

public interface PostR2dbcCustomRepository {
    Flux<Post> findAllByUserId(Long userId);
}
