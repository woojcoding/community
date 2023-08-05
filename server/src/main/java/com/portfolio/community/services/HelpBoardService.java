package com.portfolio.community.services;

import com.portfolio.community.dtos.BoardListDto;
import com.portfolio.community.dtos.BoardRequestDto;
import com.portfolio.community.dtos.BoardResponseDto;
import com.portfolio.community.enums.BoardType;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.repositories.HelpBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Board service.
 */
@Service
@RequiredArgsConstructor
public class HelpBoardService {

    /**
     * boardRepository 의존성 주입
     */
    private final HelpBoardRepository helpBoardRepository;

    /**
     * 게시글 목록 조회에서 검색 조건에 따라 게시글 정보들을 List로 받도록
     * Repository에 요청하기 위해 사용하는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return BoardListDto 게시글 정보 List
     */
    public BoardListDto getHelpBoardList(
            BoardSearchCondition boardSearchCondition) {
        boardSearchCondition.setType(BoardType.HELP);

        int pageNum = boardSearchCondition.getPageNum();

        int pageSize = boardSearchCondition.getPageSize();

        int offset = (pageNum - 1) * pageSize;

        boardSearchCondition.setOffSet(offset);

        List<BoardResponseDto> boardResponseDtoList =
                helpBoardRepository.getBoardList(boardSearchCondition);

        int totalBoardCount =
                helpBoardRepository.getTotalBoardCount(boardSearchCondition);

        return BoardListDto.builder()
                .boardResponseDtoList(boardResponseDtoList)
                .totalBoardCount(totalBoardCount)
                .build();
    }

    /**
     * 답변을 달아 업데이트하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    public void answerHelpBoard(BoardRequestDto boardRequestDto) {
        boardRequestDto.setType(BoardType.HELP);

        helpBoardRepository.answerFreeBoard(boardRequestDto);
    }


    /**
     * 답변을 달기 위해 게시글Id로 문의글을 가져오는 메서드
     *
     * @param boardId
     * @return BoardRequestDto 게시글 정보
     */
    public BoardRequestDto getHelpBoard(String boardId) {
        return helpBoardRepository.getHelpBoard(boardId);
    }

    /**
     * 게시글을 삭제하는 메서드
     *
     * @param boardId
     */
    public void deleteHelpBoard(int boardId) {
        helpBoardRepository.deleteHelpBoard(boardId);
    }
}
