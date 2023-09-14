package com.portfolio.communityuser.repository;

import com.portfolio.communityuser.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Free board mapper.
 */
@Mapper
public interface FreeBoardMapper {

    /**
     * 공지글을 저장하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    void postFreeBoard(BoardDto boardDto);

    /**
     * 공지글을 업데이트 하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    void updateFreeBoard(BoardDto boardDto);

    /**
     * 업데이트를 위해 게시글의 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return boardDto 게시글 정보
     */
    BoardDto getFreeBoard(int boardId);
}
