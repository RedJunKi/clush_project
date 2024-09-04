package com.clush.test.member;

public interface MemberService {
    MemberPostDto save(MemberPostDto memberPostDto);

    Member login(MemberLoginDto memberLoginDto);

    MemberPostDto update(Long memberId, MemberPostDto memberPostDto);

    MemberPostDto delete(Long userId);
}
