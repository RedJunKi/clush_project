package com.clush.test.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clush.test.domain.member.controller.MemberController;
import com.clush.test.domain.member.entity.Member;
import com.clush.test.domain.member.entity.MemberLoginDto;
import com.clush.test.domain.member.entity.MemberPostDto;
import com.clush.test.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = new Member("testmember@example.com", "password", "TestMember");
    }

    @Test
    void saveMember() throws Exception {
        MemberPostDto memberPostDto = new MemberPostDto("newmember@example.com", "newpassword", "NewMember");
        when(memberService.save(any(MemberPostDto.class))).thenReturn(memberPostDto);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPostDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(memberPostDto.getEmail()))
                .andExpect(jsonPath("$.username").value(memberPostDto.getUsername()));
    }

    @Test
    void login() throws Exception {
        MemberLoginDto memberLoginDto = new MemberLoginDto("testmember@example.com", "password");
        MemberPostDto memberPostDto = new MemberPostDto("testmember@example.com", "password", "TestMember");
        when(memberService.login(any(MemberLoginDto.class))).thenReturn(testMember);

        mockMvc.perform(post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberLoginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(testMember.getEmail()))
                .andExpect(jsonPath("$.username").value(testMember.getUsername()));
    }

    @Test
    void deleteMember() throws Exception {
        MemberPostDto memberPostDto = new MemberPostDto("testmember@example.com", "password", "TestMember");
        when(memberService.delete(anyLong())).thenReturn(memberPostDto);

        mockMvc.perform(delete("/api/members/{memberId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(memberPostDto.getEmail()))
                .andExpect(jsonPath("$.username").value(memberPostDto.getUsername()));
    }
}