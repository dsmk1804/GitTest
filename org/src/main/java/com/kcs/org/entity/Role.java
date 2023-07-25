package com.kcs.org.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    // ↓ 자동증가하는 칼럼 기본키가 된다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본키
    private int idx;

    // User, Admin, Manager
    private String name;

    // EAGER left 바로 하기 때문에 조회할 때 데이터를 더 많이 조회 해야 하고
    // LAZY 하게 되면 필요할 때 select 구문을 한 번 더 실행하기 때문에

    // 자원을 한꺼번에 많이 먹는 것은 EAGER
    // 자원을 천천히 먹는 것은 LAZY

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Member> members;
}

// 생성자 만드는 팁: Alt + insert -> constructor