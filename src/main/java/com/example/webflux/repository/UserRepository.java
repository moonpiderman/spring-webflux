package com.example.webflux.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    // create
    Mono<User> save(User user);

    // read
    Flux<User> findAll();

    Mono<User> findById(Long id);

    // delete
    Mono<Integer> deleteById(Long id);
}
