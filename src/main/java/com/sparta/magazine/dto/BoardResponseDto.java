package com.sparta.magazine.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long boardId;
    private String creater;
    private String content;
    private String imageUrl;
    private String grid;
    private Long likeCount;
    private LocalDateTime createdAt;
    private List<LikeResponseDto> likes;

    @Builder
    public BoardResponseDto(Long boardId, String creater, String content,
            String imageUrl, String grid, Long likeCount
            ,LocalDateTime createdAt, List<LikeResponseDto> likes){
        this.boardId = boardId;
        this.creater = creater;
        this.content = content;
        this.imageUrl = imageUrl;
        this.grid = grid;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.likes = likes;
    }
}
