package com.clush.test.domain.member.entity;


public class MemberPostDto {
    private String email;
    private String username;
    private String password;

    public MemberPostDto() {
    }

    public MemberPostDto(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public MemberPostDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
