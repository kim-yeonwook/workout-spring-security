package com.example.workoutproject.security.config;

import com.example.workoutproject.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    @Bean(name = "jwtTokenProvider")
    public JwtTokenProvider jwtTokenProvider(JwtProperties jwtProperties) {
        log.info("secret_key : "+jwtProperties.getSecret_key());
        log.info("expired_access_token : "+jwtProperties.getExpired_access_token());
        log.info("expired_refresh_token : "+jwtProperties.getExpired_refresh_token());

        return new JwtTokenProvider(jwtProperties.getSecret_key(), jwtProperties.getExpired_access_token(), jwtProperties.getExpired_refresh_token());
    }

}
