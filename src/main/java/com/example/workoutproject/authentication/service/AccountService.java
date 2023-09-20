package com.example.workoutproject.authentication.service;

import com.example.workoutproject.authentication.entity.Account;
import com.example.workoutproject.authentication.entity.AccountRepository;
import com.example.workoutproject.security.JwtTokenProvider;
import com.example.workoutproject.authentication.dto.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

     private final JwtTokenProvider jwtTokenProvider;
     private final AuthenticationManagerBuilder authenticationManagerBuilder;
     public void regist(Account account) {

     }

     public AuthToken login(String user_id, String user_pw) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user_id, user_pw);
          Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

          return jwtTokenProvider.generateToken(authentication);
     }

}
