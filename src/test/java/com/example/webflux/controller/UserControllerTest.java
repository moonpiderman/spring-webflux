package com.example.webflux.controller;

import com.example.webflux.dto.UserCreateRequest;
import com.example.webflux.dto.UserResponse;
import com.example.webflux.repository.User;
import com.example.webflux.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest(UserController.class)
@AutoConfigureWebTestClient
class UserControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    void createUser() {
        when(userService.create("greg", "greg@gmail.com")).thenReturn(
                Mono.just(new User(1L, "greg", "greg@gmail.com", LocalDateTime.now(), LocalDateTime.now()))
        );

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("greg", "greg@gmail.com"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("greg", res.getName());
                    assertEquals("greg@gmail.com", res.getEmail());
                });
    }

    @Test
    void findAll() {
        when(userService.findAll()).thenReturn(
                Flux.just(
                        new User(1L, "greg", "greg@fastcampus.co.kr", LocalDateTime.now(), LocalDateTime.now()),
                        new User(2L, "greg", "greg@fastcampus.co.kr", LocalDateTime.now(), LocalDateTime.now()),
                        new User(3L, "greg", "greg@fastcampus.co.kr", LocalDateTime.now(), LocalDateTime.now())
                ));

        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(UserResponse.class)
                .hasSize(3);
    }

    @Test
    void findById() {
        when(userService.findById(1L)).thenReturn(
                Mono.just(new User(1L, "greg", "greg@fastcampus.co.kr", LocalDateTime.now(), LocalDateTime.now())
                ));

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("greg", res.getName());
                    assertEquals("greg@fastcampus.co.kr", res.getEmail());
                });
    }

    @Test
    void updateUser() {
        when(userService.update(1L, "greg1", "greg1@fastcampus.co.kr")).thenReturn(
                Mono.just(new User(1L, "greg1", "greg1@fastcampus.co.kr", LocalDateTime.now(), LocalDateTime.now()))
        );

        webTestClient.put().uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("greg1", "greg1@fastcampus.co.kr"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("greg1", res.getName());
                    assertEquals("greg1@fastcampus.co.kr", res.getEmail());
                });
    }

    @Test
    void deleteUser() {
        when(userService.deleteById(1L)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/users/1")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}