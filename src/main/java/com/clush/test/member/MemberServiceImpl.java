package com.clush.test.member;

import com.clush.test.global.BusinessLogicException;
import com.clush.test.global.ExceptionCode;
import com.clush.test.role.Role;
import com.clush.test.role.RoleRepository;
import com.clush.test.role.RoleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberPostDto save(MemberPostDto memberPostDto) {
        ValidateDuplicateEmail(memberPostDto.getEmail());


        Member member = createMemberFromMemberPostDto(null, memberPostDto);
        setRole(member);

        Member result = memberRepository.save(member);
        return result.entityToDto();
    }

    private void setRole(Member member) {
        Role role = roleRepository.findByRoleStatus(RoleStatus.ROLE_USER);
        member.addRoles(role);
    }

    private void ValidateDuplicateEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_DUPLICATE);
        }
    }

    public MemberPostDto update(Long memberId, MemberPostDto memberPostDto) {
        Member member = createMemberFromMemberPostDto(memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)), memberPostDto);

        return member.entityToDto();
    }

    public MemberPostDto delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        memberRepository.delete(member);

        return member.entityToDto();
    }

    public Member login(MemberLoginDto memberLoginDto) {
        String email = memberLoginDto.getEmail();
        String password = memberLoginDto.getPassword();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BusinessLogicException(ExceptionCode.PASSWORD_MISMATCH);
        }

        return member;
    }


    private Member createMemberFromMemberPostDto(Member member, MemberPostDto memberPostDto) {
        if (member == null) {
            return new Member(memberPostDto.getEmail(), passwordEncoder.encode(memberPostDto.getPassword()), memberPostDto.getUsername());
        }

        member.setEmail(memberPostDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberPostDto.getPassword()));
        member.setUsername(memberPostDto.getUsername());

        return member;
    }
}
