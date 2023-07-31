package com.portfolio.community.repositories;

import com.portfolio.community.dtos.BoardRequestDto;
import com.portfolio.community.dtos.BoardResponseDto;
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
    List<BoardResponseDto> getNotificationList();

    /**
     * 공지글을 저장하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    void postNoticeBoard(BoardRequestDto boardRequestDto);

    /**
     * 공지글을 업데이트 하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    void updateNoticeBoard(BoardRequestDto boardRequestDto);

    /**
     * 업데이트를 위해 게시글의 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return BoardRequestDto 게시글 정보
     */
    BoardRequestDto getNoticeBoard(String boardId);
}
