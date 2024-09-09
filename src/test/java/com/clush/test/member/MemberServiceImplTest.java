package com.clush.test.member;

import com.clush.test.global.exception.BusinessLogicException;
import com.clush.test.global.exception.ExceptionCode;

import com.clush.test.domain.member.entity.Member;
import com.clush.test.domain.member.entity.MemberLoginDto;
import com.clush.test.domain.member.entity.MemberPostDto;
import com.clush.test.domain.member.repository.MemberRepository;
import com.clush.test.domain.member.service.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberServiceImpl memberService;

    private Member testMember;

    @BeforeEach
    void setUp() {
        // 회원 생성 및 저장
        testMember = new Member("testmember@example.com", "password", "TestMember");
        memberRepository.save(testMember);
    }

    @Test
    void save() {
        // given
        MemberPostDto memberPostDto = new MemberPostDto("newmember@example.com", "newpassword", "NewMember");

        // when
        MemberPostDto result = memberService.save(memberPostDto);

        // then
        assertThat(result.getEmail()).isEqualTo(memberPostDto.getEmail());
        assertThat(result.getUsername()).isEqualTo(memberPostDto.getUsername());
    }

    @Test
    void save_ShouldThrowException_WhenEmailIsDuplicate() {
        // given
        MemberPostDto memberPostDto = new MemberPostDto("testmember@example.com", "password", "TestMember");

        // when & then
        assertThatThrownBy(() -> memberService.save(memberPostDto))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(ExceptionCode.MEMBER_DUPLICATE.getMessage());
    }

    @Test
    void login_ShouldReturnMember_WhenCredentialsAreValid() {
        // given
        MemberLoginDto memberLoginDto = new MemberLoginDto("testmember@example.com", "password");

        // when
        Member result = memberService.login(memberLoginDto);

        // then
        assertThat(result.getEmail()).isEqualTo(testMember.getEmail());
        assertThat(result.getPassword()).isEqualTo(testMember.getPassword());
    }

    @Test
    void login_ShouldThrowException_WhenCredentialsAreInvalid() {
        // given
        MemberLoginDto memberLoginDto = new MemberLoginDto("testmember@example.com", "wrongpassword");

        // when & then
        assertThatThrownBy(() -> memberService.login(memberLoginDto))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(ExceptionCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    void delete() {
        // given
        Long memberId = testMember.getId();

        // when
        MemberPostDto result = memberService.delete(memberId);

        // then
        assertThat(result.getEmail()).isEqualTo(testMember.getEmail());
        assertThat(result.getUsername()).isEqualTo(testMember.getUsername());
        assertThat(memberRepository.findById(memberId)).isEmpty();
    }

    @Test
    void delete_ShouldThrowException_WhenMemberNotFound() {
        // given
        Long nonExistentMemberId = 999L;

        // when & then
        assertThatThrownBy(() -> memberService.delete(nonExistentMemberId))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(ExceptionCode.MEMBER_NOT_FOUND.getMessage());
    }
}