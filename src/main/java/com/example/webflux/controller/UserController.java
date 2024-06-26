package com.example.webflux.controller;

import com.example.webflux.dto.UserCreateRequest;
import com.example.webflux.dto.UserResponse;
import com.example.webflux.repository.User;
import com.example.webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> createUser(
            @RequestBody UserCreateRequest request
    ) {
        return userService.create(request.getName(), request.getEmail())
                .map(UserResponse::of);
    }

    @GetMapping("")
    public Flux<UserResponse> findAll() {
        return userService.findAll()
                .map(UserResponse::of);
    }

    @GetMapping("/{id}")
    public Mono<UserResponse> findById(
            @PathVariable("id") Long id
    ) {
        return userService.findById(id)
                .map(UserResponse::of);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserCreateRequest request
    ) {
        return userService.update(id, request.getName(), request.getEmail())
                .map(u -> ResponseEntity.ok(UserResponse.of(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> deleteUser(
            @PathVariable("id") Long id
    ) {
        return userService.deleteById(id);
    }

    @DeleteMapping("/name/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> deleteUserByName(
            @PathVariable("name") String name
    ) {
        return userService.deleteByName(name);
    }
}
