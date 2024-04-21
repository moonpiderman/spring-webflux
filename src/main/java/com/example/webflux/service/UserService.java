package com.example.webflux.service;

import com.example.webflux.repository.User;
import com.example.webflux.repository.UserR2dbcRepository;
import com.example.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserR2dbcRepository userR2dbcRepository;
    private final ReactiveRedisTemplate<String, User> reactiveRedisUserTemplate;

    private String getUserCacheKey(Long id) {
        return "users:%d".formatted(id);
    }

    // create
    public Mono<User> create(String name, String email) {
        return userR2dbcRepository.save(
                User.builder()
                        .name(name)
                        .email(email)
                        .build()
        );
    }

    // update
    public Mono<User> update(Long id, String name, String email) {
        return userR2dbcRepository.findById(id)
                .flatMap(user -> {
                    user.setName(name);
                    user.setEmail(email);
                    return userR2dbcRepository.save(user);
                })
                .flatMap(user -> reactiveRedisUserTemplate.unlink(getUserCacheKey(id))
                        .thenReturn(user)
                )
                ;
    }

    // delete
    public Mono<Void> deleteById(Long id) {
        return userR2dbcRepository.deleteById(id)
                .then(reactiveRedisUserTemplate.unlink(getUserCacheKey(id)))
                .then(Mono.empty())
                ;
    }

    public Mono<Void> deleteByName(String name) {
        return userR2dbcRepository.deleteByName(name);
    }

    // read
    public Flux<User> findAll() {
        return userR2dbcRepository.findAll();
    }


    public Mono<User> findById(Long id) {
        return reactiveRedisUserTemplate.opsForValue()
                .get(getUserCacheKey(id))
                .switchIfEmpty(
                        userR2dbcRepository.findById(id)
                                .flatMap(user -> reactiveRedisUserTemplate.opsForValue()
                                        .set(getUserCacheKey(id), user, Duration.ofSeconds(30L))
                                        .thenReturn(user)
                                )
                );
    }
}
