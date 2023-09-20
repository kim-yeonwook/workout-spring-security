package com.example.workoutproject.authentication.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountAdaptor extends User {

    private Account userEntity;

    public AccountAdaptor(Account account) {
        super(account.getUsername(), account.getPassword(), authorities(account.getAuthorities()));
        this.userEntity = account;
    }

    public Account getUserEntity() {
        return userEntity;
    }


    private static List<GrantedAuthority> authorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
    }


}
