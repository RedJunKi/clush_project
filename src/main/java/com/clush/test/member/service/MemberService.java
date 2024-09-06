package com.clush.test.member.service;

import com.clush.test.member.entity.Member;
import com.clush.test.member.entity.MemberLoginDto;
import com.clush.test.member.entity.MemberPostDto;

public interface MemberService {
    MemberPostDto save(MemberPostDto memberPostDto);

    Member login(MemberLoginDto memberLoginDto);

    MemberPostDto delete(Long userId);
}
