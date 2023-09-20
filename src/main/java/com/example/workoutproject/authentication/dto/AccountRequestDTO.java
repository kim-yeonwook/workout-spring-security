package com.example.workoutproject.authentication.dto;

import lombok.Builder;

import java.util.Set;

public record AccountRequestDTO() {

    @Builder
    public record login(
            String username,
            String password
    ) {}

    @Builder
    public record regist(
            String username,
            String password,
            String nickname,
            boolean activated,
            Set<String> authoritySet
    ) {}
}
