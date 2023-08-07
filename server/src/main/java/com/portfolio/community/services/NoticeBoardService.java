package com.portfolio.community.services;

import com.portfolio.community.dtos.BoardDto;
import com.portfolio.community.enums.BoardType;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.repositories.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Board service.
 */
@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    /**
     * boardRepository 의존성 주입
     */
    private final NoticeBoardRepository noticeBoardRepository;

    /**
     * 게시글 목록 조회에서 검색 조건에 따라 게시글 정보들을 List로 받도록
     * Repository에 요청하기 위해 사용하는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return BoardListDto 게시글 정보 List
     */
    public List<BoardDto> getNoticeBoardList(
            BoardSearchCondition boardSearchCondition) {
        boardSearchCondition.setType(BoardType.NOTICE);

        int pageNum = boardSearchCondition.getPageNum();

        int pageSize = boardSearchCondition.getPageSize();

        int offset = (pageNum - 1) * pageSize;

        boardSearchCondition.setOffSet(offset);

        return noticeBoardRepository.getBoardList(boardSearchCondition);
    }

    /**
     * 검색조건에 따른 게시글 수를 조회하는 메서드
     *
     * @param boardSearchCondition 검색조건
     * @return int 조회수
     */
    public int getTotalBoardCount(BoardSearchCondition boardSearchCondition) {
        return noticeBoardRepository.getTotalBoardCount(boardSearchCondition);
    }

    /**
     * 게시글 목록 조회에서 공지사항에서는 알림글 정보들을 List로 가져오도록
     * Repository에 요청하기 위해 사용하는 메서드
     *
     * @return BoardListDto 알림글 정보 List
     */
    public List<BoardDto> getNotificationList() {
        return noticeBoardRepository.getNotificationList();
    }

    /**
     * 공지글을 업데이트하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    public void updateNoticeBoard(BoardDto boardDto) {
        boardDto.setType(BoardType.NOTICE);

        noticeBoardRepository.updateNoticeBoard(boardDto);
    }

    /**
     * 공지글을 작성하는 메서드
     *
     * @param boardDto
     */
    public void postNoticeBoard(BoardDto boardDto) {
        boardDto.setType(BoardType.NOTICE);

        noticeBoardRepository.postNoticeBoard(boardDto);
    }

    /**
     * 게시글Id로 공지글을 가져오는 메서드
     *
     * @param boardId
     * @return boardDto 공지글 정보
     */
    public BoardDto getNoticeBoard(String boardId) {
        return noticeBoardRepository.getNoticeBoard(boardId);
    }

    /**
     * 게시글을 삭제하는 메서드
     *
     * @param boardId
     */
    public void deleteNoticeBoard(int boardId) {
        noticeBoardRepository.deleteNoticeBoard(boardId);
    }
}
