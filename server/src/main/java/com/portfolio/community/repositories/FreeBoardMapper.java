package com.portfolio.community.repositories;

import com.portfolio.community.dtos.BoardRequestDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Free board mapper.
 */
@Mapper
public interface FreeBoardMapper {

    /**
     * 공지글을 저장하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    void postFreeBoard(BoardRequestDto boardRequestDto);

    /**
     * 공지글을 업데이트 하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    void updateFreeBoard(BoardRequestDto boardRequestDto);

    /**
     * 업데이트를 위해 게시글의 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return BoardRequestDto 게시글 정보
     */
    BoardRequestDto getFreeBoard(int boardId);
}
