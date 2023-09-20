package com.example.workoutproject.authentication.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthToken {

    private String grantType;

    private String accessToken;

    private String refreshToken;
}
