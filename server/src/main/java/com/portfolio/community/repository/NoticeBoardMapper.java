package com.portfolio.community.repository;

import com.portfolio.community.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * The interface Notice board mapper.
 */
@Mapper
public interface NoticeBoardMapper {

    /**
     * 공지글 목록 조회에서 공지사항에서는 알림글 정보들을 List로 가져오는 메서드
     *
     * @return 알림글 List
     */
    List<BoardDto> getNotificationList();

    /**
     * 공지글을 저장하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    void postNoticeBoard(BoardDto boardDto);

    /**
     * 공지글을 업데이트 하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    void updateNoticeBoard(BoardDto boardDto);

    /**
     * 업데이트를 위해 게시글의 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return boardDto 게시글 정보
     */
    BoardDto getNoticeBoard(String boardId);
}
