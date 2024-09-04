package com.clush.test.member;

import com.clush.test.role.Role;
import com.clush.test.security.token.TokenResponseDto;
import com.clush.test.security.util.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenizer jwtTokenizer;

    @PostMapping
    public ResponseEntity<MemberPostDto> saveUser(@RequestBody MemberPostDto memberPostDto) {
        MemberPostDto result = memberService.save(memberPostDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Validated @RequestBody MemberLoginDto memberLoginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.login(memberLoginDto);

        List<String> roles = member.getRoles().stream()
                .map(Role::getRoleStatus)
                .map(String::valueOf)
                .toList();

        String accessToken = jwtTokenizer.createAccessToken(member.getId(), member.getEmail(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getId(), member.getEmail(), roles);

        TokenResponseDto result = new TokenResponseDto();
        result.setAccessToken(accessToken);
        result.setRefreshToken(refreshToken);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberPostDto> updateUser(@PathVariable("userId") Long memberId, @RequestBody MemberPostDto memberPostDto) {
        MemberPostDto result = memberService.update(memberId, memberPostDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MemberPostDto> deleteUser(@PathVariable("userId") Long userId) {
        MemberPostDto result = memberService.delete(userId);
        return ResponseEntity.ok(result);
    }
}
