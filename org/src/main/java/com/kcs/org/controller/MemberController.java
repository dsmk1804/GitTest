package com.kcs.org.controller;

import com.kcs.org.entity.Member;
import com.kcs.org.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("Member")
public class MemberController {

    @Autowired
    MemberService memberService;
    
    //회원가입 화면
    @GetMapping("Signup")
    public String signup(){

        return "member/signup";
    }
    
    // 회원가입 해서 테이블에다가 저장함
    @PostMapping("Signup")
    public String psignup(Member member){
        memberService.save(member);

        return "redirect:/";
    }
}
