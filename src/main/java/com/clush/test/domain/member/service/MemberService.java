package com.clush.test.domain.member.service;

import com.clush.test.domain.member.entity.MemberLoginDto;
import com.clush.test.domain.member.entity.Member;
import com.clush.test.domain.member.entity.MemberPostDto;

public interface MemberService {
    MemberPostDto save(MemberPostDto memberPostDto);

    Member login(MemberLoginDto memberLoginDto);

    MemberPostDto delete(Long userId);
}
