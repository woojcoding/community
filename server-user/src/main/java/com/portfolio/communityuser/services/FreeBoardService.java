package com.portfolio.communityuser.services;

import com.portfolio.communityuser.dtos.BoardDto;
import com.portfolio.communityuser.enums.BoardType;
import com.portfolio.communityuser.repositories.BoardSearchCondition;
import com.portfolio.communityuser.repositories.FreeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Board service.
 */
@Service
@RequiredArgsConstructor
public class FreeBoardService {

    /**
     * boardRepository 의존성 주입
     */
    private final FreeBoardRepository freeBoardRepository;

    /**
     * 게시글 목록 조회에서 검색 조건에 따라 게시글 정보들을 List로 받도록
     * Repository에 요청하기 위해 사용하는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return BoardListDto 게시글 정보 List
     */
    public List<BoardDto> getFreeBoardList(
            BoardSearchCondition boardSearchCondition) {
        boardSearchCondition.setType(BoardType.FREE);

        int pageNum = boardSearchCondition.getPageNum();

        int pageSize = boardSearchCondition.getPageSize();

        int offset = (pageNum - 1) * pageSize;

        boardSearchCondition.setOffSet(offset);

        return freeBoardRepository.getBoardList(boardSearchCondition);
    }

    /**
     * 검색조건에 따른 게시글 수를 조회하는 메서드
     *
     * @param boardSearchCondition 검색조건
     * @return int 조회수
     */
    public int getTotalBoardCount(BoardSearchCondition boardSearchCondition) {
        return freeBoardRepository.getTotalBoardCount(boardSearchCondition);
    }

    /**
     * 자유 게시글을 업데이트하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    public void updateFreeBoard(BoardDto boardDto) {
        boardDto.setType(BoardType.FREE);

        freeBoardRepository.updateFreeBoard(boardDto);
    }

    /**
     * 자유 게시글을 작성하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    public void postFreeBoard(BoardDto boardDto) {
        boardDto.setType(BoardType.FREE);

        freeBoardRepository.postFreeBoard(boardDto);
    }

    /**
     * 게시글Id로 자유게시글을 가져오는 메서드
     *
     * @param boardId
     * @return boardDto 게시글 정보
     */
    public BoardDto getFreeBoard(int boardId) {
        return freeBoardRepository.getFreeBoard(boardId);
    }

    /**
     * 게시글의 조회수를 1 증가시키는 메서드
     *
     * @param boardId 게시글 Id
     */
    public void updateViews(int boardId) {
        freeBoardRepository.updateViews(boardId);
    }

    /**
     * 게시글을 삭제하는 메서드
     *
     * @param boardId 게시글 Id
     */
    public void deleteFreeBoard(int boardId) {
        freeBoardRepository.deleteFreeBoard(boardId);
    }
}
