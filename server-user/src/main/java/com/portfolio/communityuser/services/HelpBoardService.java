package com.portfolio.communityuser.services;

import com.portfolio.communityuser.dtos.BoardDto;
import com.portfolio.communityuser.enums.BoardType;
import com.portfolio.communityuser.repositories.BoardSearchCondition;
import com.portfolio.communityuser.repositories.HelpBoardRepository;
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
    public List<BoardDto> getHelpBoardList(
            BoardSearchCondition boardSearchCondition) {
        boardSearchCondition.setType(BoardType.HELP);

        int pageNum = boardSearchCondition.getPageNum();

        int pageSize = boardSearchCondition.getPageSize();

        int offset = (pageNum - 1) * pageSize;

        boardSearchCondition.setOffSet(offset);

        return helpBoardRepository.getBoardList(boardSearchCondition);
    }

    /**
     * 검색조건에 따른 게시글 수를 조회하는 메서드
     *
     * @param boardSearchCondition 검색조건
     * @return int 조회수
     */
    public int getTotalBoardCount(BoardSearchCondition boardSearchCondition) {
        return helpBoardRepository.getTotalBoardCount(boardSearchCondition);
    }

    /**
     * 문의 게시글을 수정하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    public void updateHelpBoard(BoardDto boardDto) {
        helpBoardRepository.updateHelpBoard(boardDto);
    }

    /**
     * 문의 게시글을 수정하는 메서드
     *
     * @param boardId
     * @return boardDto 게시글 정보
     */
    public BoardDto getHelpBoard(int boardId) {
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

    /**
     * 게시글의 조회수를 1 증가시키는 메서드
     *
     * @param boardId 게시글 Id
     */
    public void updateViews(int boardId) {
        helpBoardRepository.updateViews(boardId);
    }

    /**
     * 게시글을 저장하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    public void postHelpBoard(BoardDto boardDto) {
        boardDto.setType(BoardType.HELP);

        helpBoardRepository.postBoard(boardDto);
    }
}
