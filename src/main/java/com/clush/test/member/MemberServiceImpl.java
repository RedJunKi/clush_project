package com.clush.test.member;

import com.clush.test.global.BusinessLogicException;
import com.clush.test.global.ExceptionCode;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberPostDto save(MemberPostDto memberPostDto) {
        ValidateDuplicateEmail(memberPostDto.getEmail());

        Member member = new Member(memberPostDto.getEmail(), memberPostDto.getPassword(), memberPostDto.getUsername());
        Member result = memberRepository.save(member);

        return result.entityToDto();
    }

    private void ValidateDuplicateEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_DUPLICATE);
        }
    }

    @Override
    public Member login(MemberLoginDto memberLoginDto) {
        Member result = memberRepository.findByEmail(memberLoginDto.getEmail())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        if (!result.getPassword().equals(memberLoginDto.getPassword())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        return result;
    }

    public MemberPostDto delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        memberRepository.delete(member);

        return member.entityToDto();
    }
}
