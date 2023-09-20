package com.example.workoutproject.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret_key;
    private Long expired_access_token;
    private Long expired_refresh_token;

}
