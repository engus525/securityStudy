package com.example.securitystudy.controller;

import com.example.securitystudy.domain.Member;
import com.example.securitystudy.dto.MemberJoinDto;
import com.example.securitystudy.dto.MemberLoginDto;
import com.example.securitystudy.repository.MemberRepository;
import com.example.securitystudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Optional;

@Controller
public class ViewController
{
    private final MemberService memberService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public ViewController(MemberService memberService)
    {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }

    @GetMapping("/join")
    public String joinPage()
    {
        return "/join";
    }

    @GetMapping("/loginSuccess")
    public String successPage(@AuthenticationPrincipal User user, Model model)
    {
        model.addAttribute("username", user.getUsername());
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        GrantedAuthority role = authorities.stream().findFirst().get();
        model.addAttribute("role", role);
        return "/loginSuccess";
    }


    @GetMapping("/admin")
    public String adminPage()
    {
        return "/adminPage";
    }

    @GetMapping("/user")
    public String userPage()
    {
        return "/userPage";
    }

    @PostMapping("/joinProc")
    public String joinProc(MemberJoinDto dto)
    {
        try
        {
            memberService.save(dto.getUsername(), dto.getPw());
            return "redirect:/";
        } catch (Exception e)
        {
            return "redirect:/join";
        }
    }

}
