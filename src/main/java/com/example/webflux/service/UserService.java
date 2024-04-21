package com.example.webflux.service;

import com.example.webflux.repository.User;
import com.example.webflux.repository.UserR2dbcRepository;
import com.example.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserR2dbcRepository userR2dbcRepository;

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
        // 1. 사용자를 찾는다.
        // 2. 데이터를 변경하고 저장한다.
        return userR2dbcRepository.findById(id)
                .flatMap(user -> {
                    user.setName(name);
                    user.setEmail(email);
                    return userR2dbcRepository.save(user);
                });
    }

    // delete
    public Mono<Void> deleteById(Long id) {
        return userR2dbcRepository.deleteById(id);
    }

    public Mono<Void> deleteByName(String name) {
        return userR2dbcRepository.deleteByName(name);
    }

    // read
    public Flux<User> findAll() {
        return userR2dbcRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        return userR2dbcRepository.findById(id);
    }
}
