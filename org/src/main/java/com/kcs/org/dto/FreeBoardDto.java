package com.kcs.org.dto;

import com.kcs.org.entity.FreeBoard;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Getter
@Setter
@Component
@ToString
public class FreeBoardDto {
    private int idx;

    private String name;

    @Size(min = 3, max = 30)
    private String title;

    @Size(min = 3, max = 30)
    private String content;
    private LocalDateTime createDate;

    private static ModelMapper modelMapper = new ModelMapper();

    public FreeBoard createFreeBoard(){
        return modelMapper.map(this, FreeBoard.class);
    }

    public static FreeBoardDto of(FreeBoard freeBoard){
        return modelMapper.map( freeBoard, FreeBoardDto.class);
    }

}
