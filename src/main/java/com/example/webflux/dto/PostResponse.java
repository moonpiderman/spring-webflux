package com.example.webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class PostResponse {
    private String id;
    private String title;
}
