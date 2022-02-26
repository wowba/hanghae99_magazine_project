package com.sparta.magazine.service;

import com.sparta.magazine.dto.BoardRequestDto;
import com.sparta.magazine.dto.BoardResponseDto;
import com.sparta.magazine.dto.LikeResponseDto;
import com.sparta.magazine.exception.ErrorCodeException;
import com.sparta.magazine.model.Board;
import com.sparta.magazine.model.Likelist;
import com.sparta.magazine.model.User;
import com.sparta.magazine.repository.BoardRepository;
import com.sparta.magazine.repository.LikelistRepository;
import com.sparta.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sparta.magazine.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikelistRepository likelistRepository;

    // 전체 게시판 불러오기
    @Transactional
    public List<BoardResponseDto> getAllBoard(){

        // 모든 게시글 가져오기
        List<Board> boardList = boardRepository.findAll();

        // 게시글을 반환해서 저장할 리스트
        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();

        // 게시글 덩어리 해쳐서 넣어주기
        for(Board board : boardList){

            // 좋아요 리스트 가져오기
            List<Likelist> likelists = likelistRepository.findLikelistsByBoard_Id(board.getId());
            // 좋아요 수
            Long likeCount = (long) likelists.size();
            // 좋아요 리스트 가공하기 (프론트 단에서 처리할 것)
            // 백엔드 에서 게시글 좋아요를 눌렀는지 판별할 수 있도록 확인하는 기능을 넣으면 좋을텐데...
            // 우선은 프론트에서 확인하는걸로 협의.
            List<LikeResponseDto> likeResponseDtos = new ArrayList<>();
            for(Likelist likelist : likelists){
                LikeResponseDto likeResponseDto = new LikeResponseDto(likelist.getUser().getId());
                likeResponseDtos.add(likeResponseDto);
            }
            // BoardResponseDto 생성
            BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                    .boardId(board.getId())
                    .creater(board.getUsername())
                    .content(board.getContent())
                    .imageUrl(board.getImageUrl())
                    .grid(board.getGrid())
                    .likeCount(likeCount)
                    .createdAt(board.getCreatedAt())
                    .likes(likeResponseDtos)
                    .build();
            // 반환할 리스트에 저장하기
            boardResponseDtos.add(boardResponseDto);
        }

        return boardResponseDtos;
    }

    // 상세 게시판 불러오기
    @Transactional
    public BoardResponseDto getBoard(Long id){

        // 게시글 정보 가져오기
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ErrorCodeException(BOARD_NOT_FOUND));

        // 좋아요 리스트 가져오기
        List<Likelist> likelists = likelistRepository.findLikelistsByBoard_Id(id);
        // 좋아요 수
        Long likeCount = (long) likelists.size();
        // 좋아요 리스트 가공하기 (프론트 단에서 처리할 것)
        // 백엔드 에서 좋아요 눌렀는지 판별할 수 있도록 확인하는 기능을 넣으면 좋을텐데...
        // 우선은 프론트에서 확인하는걸로 협의.
        List<LikeResponseDto> likeResponseDtos = new ArrayList<>();
        for(Likelist likelist : likelists){
            LikeResponseDto likeResponseDto = new LikeResponseDto(likelist.getUser().getId());
            likeResponseDtos.add(likeResponseDto);
        }

        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                                        .boardId(board.getId())
                                        .creater(board.getUsername())
                                        .content(board.getContent())
                                        .imageUrl(board.getImageUrl())
                                        .grid(board.getGrid())
                                        .likeCount(likeCount)
                                        .createdAt(board.getCreatedAt())
                                        .likes(likeResponseDtos)
                                        .build();
        return boardResponseDto;
    }

    // 게시판 생성
    @Transactional
    public Long createBoard(BoardRequestDto boardRequestDto) {

        // 게시판 빌더 생성
        Board board = Board.builder()
                    .username(boardRequestDto.getUsername())
                    .imageUrl(boardRequestDto.getImageUrl())
                    .grid(boardRequestDto.getGrid())
                    .content(boardRequestDto.getContent())
                    .build();
        // imageUrl 존재하는지 확인 - 프론트와 협의 후 추가예정

        // 게시글을 만드려는 유저가 존재하는지 확인
        usernameIsExist(boardRequestDto.getUsername());
        // 연관관계 편의 메소드
        User user = userRepository.findUserByUsername(boardRequestDto.getUsername());
        board.SetUser(user);
        // 게시판 저장하기
        boardRepository.save(board);

        return board.getId();
    }

    // 게시글을 생성하려는 유저가 존재하는지 확인
    private void usernameIsExist(String username) {
        Optional<User> findUsername = userRepository.findByUsername(username);
        if(!findUsername.isPresent()){
            throw new ErrorCodeException(USER_NOT_FOUND);
        }
    }

    // imageUrl 존재하는지 확인 (프론트와 협의 후 기능 추가 예정) 
    private void imageUrlIsExist(Board board) {
        if(Objects.equals(board.getImageUrl(), "")){
            throw new IllegalArgumentException("포스팅할 이미지 주소를 넣어주세요!");
        }
    }

    // 게시판 수정
    @Transactional
    public void editBoard(Long id, BoardRequestDto boardRequestDto) {
        
        // 수정하려는 유저 조회
        User user = userRepository.findByUsername(boardRequestDto.getUsername()).orElseThrow(
                () -> new ErrorCodeException(USER_NOT_FOUND));

        // 수정하려는 유저가 게시판의 생성자인지 확인
        if(!Objects.equals(boardRequestDto.getUsername(), user.getUsername())){
            throw new ErrorCodeException(BOARD_EDIT_OR_DELETE_NOT_MATCH);
        }
        
        // 수정할 게시판이 존재하는지 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ErrorCodeException(BOARD_NOT_FOUND));
        board.Edit(boardRequestDto);
    }

    // 게시판 삭제
    @Transactional
    public void deleteBoard(Long id, User user ) {

        String username = user.getUsername();

        // 삭제하려는 게시판이 존재하는지 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ErrorCodeException(BOARD_NOT_FOUND));

        // 삭제하려는 유저가 작성자인지 확인.
        if(!(Objects.equals(username, board.getUsername()))) {
            throw new ErrorCodeException(BOARD_EDIT_OR_DELETE_NOT_MATCH);
        }

        // 유저 정보를 받아와서 삭제를 제한할 수 있는 기능이 필요하다.
        boardRepository.deleteById(id);
    }
}
