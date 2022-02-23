package com.sparta.magazine.model.responseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardSuccess {
    private String result;
    private String msg;
    private Long boardId;
}
