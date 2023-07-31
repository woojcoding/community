package com.portfolio.community.repositories;

import com.portfolio.community.dtos.BoardRequestDto;
import com.portfolio.community.dtos.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Board repository.
 */
@Repository
@RequiredArgsConstructor
public class BoardRepository {

    /**
     * board에 대한 db조작을 하는 인터페이스 의존성 주입
     */
    private final BoardMapper boardMapper;

    private final NoticeBoardMapper noticeBoardMapper;

    /**
     * 게시글 목록 조회에서  검색 조건에 따라 게시글 정보들을 List로 가져오는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return List<BoardResponseDto>       게시글 정보 List
     */
    public List<BoardResponseDto> getBoardList(
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
     * 공지글을 수정하기 위해 게시글Id에 해당하는 공지글을 가져오는 메서드
     *
     * @param boardId
     * @return
     */
    public BoardRequestDto getNoticeBoard(String boardId) {
        return noticeBoardMapper.getNoticeBoard(boardId);
    }

    /**
     * 게시글 목록 조회에서 공지사항에서는 알림글 정보들을 List로 가져오는 메서드
     *
     * @return 알림글 List
     */
    public List<BoardResponseDto> getNotificationList() {
        return noticeBoardMapper.getNotificationList();
    }

    /**
     * 게시글을 저장하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    public void postNoticeBoard(BoardRequestDto boardRequestDto) {
        noticeBoardMapper.postNoticeBoard(boardRequestDto);
    }

    /**
     * 게시글을 업데이트 하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    public void updateNoticeBoard(BoardRequestDto boardRequestDto) {
        noticeBoardMapper.updateNoticeBoard(boardRequestDto);
    }
}
