package com.example.workoutproject.member.dto;

import lombok.Builder;

@Builder
public record Member(
        String username,
        String usernick
) {
}
