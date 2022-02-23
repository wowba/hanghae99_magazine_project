package com.sparta.magazine.model.responseEntity;

import com.sparta.magazine.dto.BoardResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetMultiBoard {
    private String result;
    private String msg;
    private List<BoardResponseDto> boardResponseDtos;

}


