package com.clush.test.domain.member.controller;

import com.clush.test.domain.member.entity.Member;
import com.clush.test.domain.member.entity.MemberLoginDto;
import com.clush.test.domain.member.entity.MemberPostDto;
import com.clush.test.domain.member.service.MemberService;
import com.clush.test.global.annotation.CheckSession;
import com.clush.test.global.exception.BusinessLogicException;
import com.clush.test.global.exception.ExceptionCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member", description = "멤버 API")
@RequestMapping("/api/members")
public class MemberController {

    private final int SESSION_MAINTAIN_TIME = 60 * 60;
    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "멤버 생성", description = "회원가입하기 (이메일, 이름, 패스워드)")
    public ResponseEntity<MemberPostDto> saveMember(@RequestBody MemberPostDto memberPostDto) {
        MemberPostDto result = memberService.save(memberPostDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "세션 로그인 (이메일, 패스워드)")
    public ResponseEntity<MemberPostDto> login(@RequestBody MemberLoginDto memberLoginDto, HttpSession session) {
        Member member = memberService.login(memberLoginDto);

        session.setAttribute("memberId", member.getId());
        session.setMaxInactiveInterval(SESSION_MAINTAIN_TIME);

        return ResponseEntity.ok(member.entityToDto());
    }

    @DeleteMapping("/{memberId}")
    @Operation(summary = "멤버 삭제", description = "회원가입한 멤버 삭제하기")
    @CheckSession
    public ResponseEntity<MemberPostDto> deleteMember(@PathVariable("memberId") Long memberId, HttpSession session) {
        Long loginMemberId = (Long) session.getAttribute("memberId");

        if (!memberId.equals(loginMemberId)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_TYPE);
        }

        MemberPostDto result = memberService.delete(memberId);
        return ResponseEntity.ok(result);
    }
}
