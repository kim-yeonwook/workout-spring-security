package com.example.workoutproject.member.controller;

import com.example.workoutproject.member.dto.Member;
import com.example.workoutproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Member>> getMemberList(@RequestParam(name = "page") Integer page,
                                                      @RequestParam(name = "size") Integer size) {
        List<Member> memberList = null;
        try {
            if (ObjectUtils.isEmpty(page)||ObjectUtils.isEmpty(size)) throw new Exception();

            memberList = memberService.getMemberList(page, size);

            return new ResponseEntity<>(memberList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(memberList, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userid}")
    public void getMember(@PathVariable(name = "userid") String userid) {

    }
}
