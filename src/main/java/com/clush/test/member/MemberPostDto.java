package com.clush.test.member;


public class MemberPostDto {

    private Long id;
    private String email;
    private String username;
    private String password;

    public MemberPostDto() {
    }

    public MemberPostDto(Long id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
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
