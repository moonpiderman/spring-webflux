package com.example.webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    private Long userId;
    private String title;
    private String content;

    public static PostCreateRequest of(Long userId, String title, String content) {
        return PostCreateRequest.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .build();
    }
}
