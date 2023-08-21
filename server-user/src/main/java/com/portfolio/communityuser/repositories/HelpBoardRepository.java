package com.portfolio.communityuser.repositories;

import com.portfolio.communityuser.dtos.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Help Board repository.
 */
@Repository
@RequiredArgsConstructor
public class HelpBoardRepository {

    /**
     * 게시글에 대한 공통 db조작을 하는 인터페이스 의존성 주입
     */
    private final BoardMapper boardMapper;

    /**
     *  문의 게시글에 대한 db조작을 하는 인터페이스 의존성 주입
     */
    private final HelpBoardMapper helpBoardMapper;

    /**
     * 게시글 목록 조회에서  검색 조건에 따라 게시글 정보들을 List로 가져오는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return List<BoardResponseDto>       게시글 정보 List
     */
    public List<BoardDto> getBoardList(
            BoardSearchCondition boardSearchCondition
    ) {
        return boardMapper.getBoardList(boardSearchCondition);
    }

    /**
     * 게시글 목록 조회에서  검색 조건에 따라 검색 되는 게시글의 총 수
     *
     * @param boardSearchCondition 검색 조건
     * @return 게시글 조회 건 수
     */
    public int getTotalBoardCount(
            BoardSearchCondition boardSearchCondition
    ) {
        return boardMapper.getTotalBoardCount(boardSearchCondition);
    }

    /**
     * 게시글의 정보를 가져오는 메서드
     *
     * @param boardId
     * @return BoardDto
     */
    public BoardDto getHelpBoard(int boardId) {
        return helpBoardMapper.getHelpBoard(boardId);
    }

    /**
     * 문의 게시글을 수정하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    public void updateHelpBoard(BoardDto boardDto) {
        helpBoardMapper.updateHelpBoard(boardDto);
    }

    /**
     * 게시글을 삭제하는 메서드
     *
     * @param boardId
     */
    public void deleteHelpBoard(int boardId) {
        boardMapper.deleteBoard(boardId);
    }

    /**
     * 게시글의 조회수를 1 증가시키는 메서드
     *
     * @param boardId 게시글 Id
     */
    public void updateViews(int boardId) {
        boardMapper.updateViews(boardId);
    }
}
