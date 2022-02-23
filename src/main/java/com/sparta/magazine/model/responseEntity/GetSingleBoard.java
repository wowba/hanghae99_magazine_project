package com.sparta.magazine.model.responseEntity;

import com.sparta.magazine.dto.BoardResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetSingleBoard {
    private String result;
    private String msg;
    private BoardResponseDto boardResponseDto;
}
