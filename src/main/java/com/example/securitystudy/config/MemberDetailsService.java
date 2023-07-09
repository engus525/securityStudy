package com.example.securitystudy.config;

import com.example.securitystudy.domain.Member;
import com.example.securitystudy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberDetailsService implements UserDetailsService
{
    private final MemberService memberService;
    @Autowired
    public MemberDetailsService(MemberService memberService)
    {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<Member> one = memberService.findOne(username);
        Member member = one.orElseThrow(() -> new UsernameNotFoundException("Member Not Found! 씨빠꺼"));

        return User.builder()
                .username(member.getUsername())
                .password(member.getPw())
                .roles(member.getRole())
                .build();
    }
}
