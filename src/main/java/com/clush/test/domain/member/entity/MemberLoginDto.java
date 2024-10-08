package com.clush.test.domain.member.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MemberLoginDto {

    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;

    public MemberLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
