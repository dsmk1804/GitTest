package com.kcs.org.controller;

import com.kcs.org.entity.Role;
import com.kcs.org.repository.FreeBoardRepository;
import com.kcs.org.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {


    private final FreeBoardRepository freeBoardRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public MainController(FreeBoardRepository freeBoardRepository, RoleRepository roleRepository) {
        this.freeBoardRepository = freeBoardRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String index(Authentication authentication){

        Role role1 = Role.builder().idx(1).name("USER").build();
        Role role2 = Role.builder().idx(2).name("ADMIN").build();
        roleRepository.save(role1);
        roleRepository.save(role2);

//        if (authentication != null) {
//            System.out.println("로그인유무 = " + authentication.isAuthenticated());
//            System.out.println("getPrincipal = " + authentication.getPrincipal());
//            System.out.println("userdetails = " + authentication.getDetails());
//        }
//        FreeBoard freeBoard = freeBoardRepository.myQuery(1);
//        System.out.println(freeBoard);
//        FreeBoard f1 = new FreeBoard().builder()
//                .name("홍길동")
//                .content("zzzzz")
//                .build();
//        freeBoardRepository.save(f1);
        return "index";

    }
}
