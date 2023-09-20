package com.example.workoutproject.authentication.controller;

import com.example.workoutproject.authentication.dto.AccountRequestDTO;
import com.example.workoutproject.authentication.dto.AuthToken;
import com.example.workoutproject.authentication.entity.Account;
import com.example.workoutproject.authentication.entity.Authority;
import com.example.workoutproject.authentication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class AccountController {

    private final AccountService authService;

    @PostMapping("regist")
    public void regist(@RequestBody AccountRequestDTO.regist request) throws Exception {
        Account account = Account.builder()
                .username(request.username())
                .password(request.password())
                .tokenWeight(0l)
                .nickname(request.nickname())
                .activated(request.activated())
                .authorities(request.authoritySet().stream().map(authorities -> Authority.builder().authorityName(authorities).build()).collect(Collectors.toSet()))
                .build();

        authService.regist(account);
    }

    @PostMapping("login")
    public ResponseEntity<AuthToken> login(@RequestBody AccountRequestDTO.login request) throws Exception {
        if (ObjectUtils.isEmpty(request.username())||ObjectUtils.isEmpty(request.password()))
            throw new Exception();
        
        AuthToken token = authService.login(request.username(), request.password());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
