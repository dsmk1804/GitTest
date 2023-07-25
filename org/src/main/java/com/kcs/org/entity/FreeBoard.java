package com.kcs.org.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FreeBoard extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;

    private String name;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    @JsonIgnore
    private Member member;
}
