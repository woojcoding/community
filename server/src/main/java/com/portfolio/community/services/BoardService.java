package com.portfolio.community.services;

import com.portfolio.community.dtos.BoardListDto;
import com.portfolio.community.dtos.BoardRequestDto;
import com.portfolio.community.dtos.BoardResponseDto;
import com.portfolio.community.repositories.BoardInfoRepository;
import com.portfolio.community.repositories.BoardRepository;
import com.portfolio.community.repositories.BoardSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Board service.
 */
@Service
@RequiredArgsConstructor
public class BoardService {

    /**
     * boardRepository 의존성 주입
     */
    private final BoardRepository boardRepository;

    private final BoardInfoRepository boardInfoRepository;

    /**
     * 게시글 목록 조회에서 검색 조건에 따라 게시글 정보들을 List로 받도록
     * Repository에 요청하기 위해 사용하는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return BoardListDto 게시글 정보 List
     */
    public BoardListDto getBoardList(
            BoardSearchCondition boardSearchCondition) {
        int pageNum = boardSearchCondition.getPageNum();

        int pageSize = boardSearchCondition.getPageSize();

        int offset = (pageNum - 1) * pageSize;

        boardSearchCondition.setOffSet(offset);

        List<BoardResponseDto> boardResponseDtoList =
                boardRepository.getBoardList(boardSearchCondition);

        int totalBoardCount
                = boardRepository.getTotalBoardCount(boardSearchCondition);

        return BoardListDto.builder()
                .boardResponseDtoList(boardResponseDtoList)
                .totalBoardCount(totalBoardCount)
                .build();
    }

    /**
     * 게시글 목록 조회에서 공지사항에서는 알림글 정보들을 List로 가져오도록
     * Repository에 요청하기 위해 사용하는 메서드
     *
     * @return BoardListDto 알림글 정보 List
     */
    public BoardListDto getNotificationList() {
        List<BoardResponseDto> boardResponseDtoList =
                boardRepository.getNotificationList();

        return BoardListDto.builder()
                .boardResponseDtoList(boardResponseDtoList)
                .build();
    }

    /**
     * 게시글을 저장하는 메서드
     *
     * @param boardRequestDto 게시글 정보 Dto
     */
    public void postBoard(BoardRequestDto boardRequestDto) {
        // board_info 테이블에서 type으로 id를 조회 후 지정
        int boardInfoId = getBoardInfoId(boardRequestDto.getType());

        boardRequestDto.setBoardInfoId(boardInfoId);

        // 배열형태이기에 콤마 제거
        String status = boardRequestDto.getStatus().replaceAll(",", "");

        boardRequestDto.setStatus(status);

        boardRepository.postBoard(boardRequestDto);
    }

    /**
     * board_info 테이블에서 type으로 id를 조회하는 메서드
     *
     * @param type 게시글 타입
     * @return
     */
    private int getBoardInfoId(String type) {
        return boardInfoRepository.getBoardInfoId(type);
    }
}
