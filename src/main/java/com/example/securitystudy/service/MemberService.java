package com.example.securitystudy.service;

import com.example.securitystudy.domain.Member;
import com.example.securitystudy.dto.MemberJoinDto;
import com.example.securitystudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService
{
    private final MemberRepository memberRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberService(MemberRepository repository)
    {
        this.memberRepository = repository;
    }

    public Optional<Member> findOne(String username)
    {
        return memberRepository.findByUsername(username);
    }

    public Long save(String username, String pw)
    {
        Member member = Member.createMember(username, pw, bCryptPasswordEncoder);
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member)
    {
        memberRepository.findByUsername(member.getUsername())
                .ifPresent(m ->
                {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

}
