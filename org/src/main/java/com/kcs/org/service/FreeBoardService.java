package com.kcs.org.service;

import com.kcs.org.dto.FreeBoardDto;
import com.kcs.org.entity.FreeBoard;
import com.kcs.org.repository.FreeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class FreeBoardService {
    @Autowired
    FreeBoardRepository freeBoardRepository;

//    @Autowired
//    JPAQueryFactory jpaQueryFactory;

    public Page<FreeBoard> list(String searchText,String SearchText, Pageable pageable) {
        //        Pageable pageable = PageRequest.of(0,5, Sort.by("idx").descending());
        //        Page<FreeBoard> pagelist = freeBoardRepository.findAll(pageable);
        Page<FreeBoard> pagelist = freeBoardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        System.out.println(pagelist.getContent());
        return pagelist;
    }

    @Autowired
    public boolean insert(FreeBoardDto dto){
        // 작성일자 없어지는거 수정
        FreeBoard freeBoardEntity = freeBoardRepository.findById(dto.getIdx()).orElse(new FreeBoard());
        freeBoardEntity.setContent(dto.getContent());
        freeBoardEntity.setName(dto.getName());
        freeBoardEntity.setTitle(dto.getTitle());
        freeBoardRepository.save(freeBoardEntity);
        return true;
    }
    public FreeBoardDto getRow(FreeBoardDto freeBoardDto) {
        FreeBoard freeBoard = freeBoardRepository.findById(freeBoardDto.getIdx())
                .orElse(new FreeBoard());
        return FreeBoardDto.of(freeBoard);
    }
}
