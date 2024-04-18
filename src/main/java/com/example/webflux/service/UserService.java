package com.example.webflux.service;

import com.example.webflux.repository.User;
import com.example.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // create
    public Mono<User> create(String name, String email) {
        return userRepository.save(
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
        return userRepository.findById(id)
                .flatMap(user -> {
                    user.setName(name);
                    user.setEmail(email);
                    return userRepository.save(user);
                });
    }

    // delete
    public Mono<Integer> deleteById(Long id) {
        return userRepository.deleteById(id);
    }

    // read
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
