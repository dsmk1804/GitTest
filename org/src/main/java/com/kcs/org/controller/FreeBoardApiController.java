package com.kcs.org.controller;

import com.kcs.org.entity.FreeBoard;
import com.kcs.org.repository.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class FreeBoardApiController {

    @Autowired
    FreeBoardRepository freeBoardRepository;

    @GetMapping("list")
    List<FreeBoard> list(){
        return freeBoardRepository.findAll();
    }

}
