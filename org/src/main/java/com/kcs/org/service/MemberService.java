package com.kcs.org.service;


import com.kcs.org.entity.Member;
import com.kcs.org.entity.Role;
import com.kcs.org.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


@Service
public class MemberService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("오시나용" + username);
        Member member = memberRepository.findByUsername(username);
        System.out.println(member);
        if(member == null){
            throw new UsernameNotFoundException("그런 회원 그없");
        }

        return User.builder()
                .username(username)
                .password(member.getPassword())
                .roles(new String[]{"USER",})
                .build();
    }

    public void save(Member member) {
        // 회원 중복 확인
        boolean result = validate(member);
        if(result) {
            // 패스워드는 암호화 해서 저장
            member.setPassword(passwordEncoder.encode(member.getPassword()));

            // 롤 적용
            member.setRoles(Arrays.asList(Role.builder().idx(1).build()));
            memberRepository.save(member);
        }
    }

    public boolean validate(Member member){
        Member dbmember =
                memberRepository.findByUsername(member.getUsername());
        if(dbmember == null)
            return true;
        else
            throw new IllegalStateException("이미 가입된 회원입니다.");
    }

}
