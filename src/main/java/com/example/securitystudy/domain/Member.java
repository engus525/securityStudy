package com.example.securitystudy.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
public class Member
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId()
    {
        return id;
    }

    @Column(unique = true)
    private String username;
    private String pw;
    private String role;

    public String getUsername()
    {
        return username;
    }

    public String getPw()
    {
        return pw;
    }

    public String getRole()
    {
        return role;
    }

    protected Member(){};
    public Member(Long id, String username, String pw, String role)
    {
        this.id = id;
        this.username = username;
        this.pw = pw;
        this.role = role;
    }

    public static Member createMember(String username, String pw, BCryptPasswordEncoder encoder)
    {
        return new Member(null,username,encoder.encode(pw),"USER");
    }
}
