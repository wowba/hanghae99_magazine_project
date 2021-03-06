package com.sparta.magazine.api;

import com.sparta.magazine.dto.BoardRequestDto;
import com.sparta.magazine.dto.BoardResponseDto;
import com.sparta.magazine.model.User;
import com.sparta.magazine.model.responseEntity.BoardSuccess;
import com.sparta.magazine.model.responseEntity.GetMultiBoard;
import com.sparta.magazine.model.responseEntity.GetSingleBoard;
import com.sparta.magazine.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardService boardService;

//    // 전체 게시판 가져오기 ( 무한스크롤 미적용 )
//    @GetMapping("/api/board")
//    public ResponseEntity<GetMultiBoard> getMultiBoards(){
//        List<BoardResponseDto> boardResponseDtos = boardService.getAllBoard();
//        return new ResponseEntity<>(new GetMultiBoard("success", "모든 게시판 가져오기 성공", boardResponseDtos), HttpStatus.OK);
//    }

    // 전체 게시판 가져오기 ( 무한스크롤 )
    @GetMapping("/api/board")
    public ResponseEntity<GetMultiBoard> getMultiBoards(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy
    ){
        return boardService.getAllBoard(page, size, sortBy);
    }

    // 상세 게시판 가져오기
    @GetMapping("/api/board/{id}")
    public ResponseEntity<GetSingleBoard> getSingleBoard(@PathVariable Long id) {
        BoardResponseDto boardResponseDto = boardService.getBoard(id);
        return new ResponseEntity<>(new GetSingleBoard("success","상세 게시판 가져오기 성공", boardResponseDto), HttpStatus.OK);
    }

    // 게시판 생성하기
    @PostMapping("/api/board")
    public ResponseEntity<BoardSuccess> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        Long id = boardService.createBoard(boardRequestDto);
        return new ResponseEntity<>(new BoardSuccess("success", "게시판 생성 성공.", id), HttpStatus.OK);
    }

    // 게시판 수정하기
    @PutMapping("/api/board/{id}")
    public ResponseEntity<BoardSuccess> editBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        boardService.editBoard(id, boardRequestDto);
        return new ResponseEntity<>(new BoardSuccess("success", "해당 게시판(" + id +")이 수정 되었습니다.", id), HttpStatus.OK);
    }

    // 게시판 삭제하기
    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<BoardSuccess> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal User user){
        boardService.deleteBoard(id, user);
        return new ResponseEntity<>(new BoardSuccess("success", "해당 게시판(" + id +")이 삭제 되었습니다.", id), HttpStatus.OK);
    }
}