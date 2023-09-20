package com.example.workoutproject.security.config;

import com.example.workoutproject.security.filter.CustomJwtFilter;
import com.example.workoutproject.security.handler.JwtAccessDeniedHandler;
import com.example.workoutproject.security.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 기본적인 웹보안을 활성화하겠다
@EnableMethodSecurity // @PreAuthorize 어노테이션 사용을 위해 선언
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, CustomJwtFilter customJwtFilter, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvRequestMatcher = new MvcRequestMatcher.Builder(introspector);

        httpSecurity
                // csrf 사용안함
                .csrf(csrs -> {
                    csrs.disable();
                })

                // 예외 발생시 처리 핸들러
                .exceptionHandling(handlingConfigurer -> {
                    handlingConfigurer
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                            .accessDeniedHandler(jwtAccessDeniedHandler);
                })

                // enable h2-console // embedded h2를 위한 설정
                .headers(headersConfigurer -> {
                    headersConfigurer.frameOptions(frameOptionsConfig -> {
                        frameOptionsConfig.sameOrigin();
                    });
                })


                // 요청에 대한 인증 여부 관리
                .authorizeHttpRequests(authorizeRequest -> {
                    authorizeRequest
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

                            // mvc 패턴의 url은 mvcRequestMacher 혹은 antMatcher를 사용
                            .requestMatchers(mvRequestMatcher.pattern("/login")).permitAll()
                            .requestMatchers(mvRequestMatcher.pattern("/regist")).permitAll()
                            // 정의 되지 않은 요청은 인증 필요
                            .anyRequest().authenticated();
                })

                // 세션 사용 X
                .sessionManagement(sessionManagementConfigurer -> {
                    sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
