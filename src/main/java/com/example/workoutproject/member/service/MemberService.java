package com.example.workoutproject.member.service;

import com.example.workoutproject.authentication.entity.Account;
import com.example.workoutproject.authentication.entity.AccountRepository;
import com.example.workoutproject.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final AccountRepository accountRepository;

    public List<Member> getMemberList(int index, int size) {
        Page<Account> list = accountRepository.findAll(PageRequest.of(index, size));
        return list.stream()
                .map(account -> Member.builder()
                        .username(account.getUsername())
                        .usernick(account.getNickname())
                        .build()
                )
                .toList();
    }
}
