package com.kcs.org.repository;

import com.kcs.org.entity.FreeBoard;

import com.kcs.org.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Integer> {

    // select * from freeboard where title like '%title' or content like '%content%' Limit 0,5 ;
//    Page<FreeBoard> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

//    @Query("select board from FreeBoard where idx=?1")
//    FreeBoard myQuery(int idx);

    // select * from where username = ?1 이 생성된다
    public Member findByUsername(String username);
}
