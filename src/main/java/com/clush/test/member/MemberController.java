package com.clush.test.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final int SESSION_MAINTAIN_TIME = 60 * 60;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberPostDto> saveMember(@RequestBody MemberPostDto memberPostDto) {
        MemberPostDto result = memberService.save(memberPostDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberPostDto> login(@RequestBody MemberLoginDto memberLoginDto, HttpSession session) {
        Member member = memberService.login(memberLoginDto);

        session.setAttribute("memberId", member.getId());
        session.setMaxInactiveInterval(SESSION_MAINTAIN_TIME);

        return ResponseEntity.ok(member.entityToDto());
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<MemberPostDto> deleteMember(@PathVariable("memberId") Long memberId) {
        MemberPostDto result = memberService.delete(memberId);
        return ResponseEntity.ok(result);
    }
}
