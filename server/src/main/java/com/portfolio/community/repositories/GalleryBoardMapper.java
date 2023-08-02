package com.portfolio.community.repositories;

import com.portfolio.community.dtos.BoardRequestDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Gallery board mapper.
 */
@Mapper
public interface GalleryBoardMapper {

    /**
     * 갤러리 글을 저장하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    void postGalleryBoard(BoardRequestDto boardRequestDto);

    /**
     * 갤러리 글을 업데이트 하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    void updateGalleryBoard(BoardRequestDto boardRequestDto);

    /**
     * 업데이트를 위해 게시글의 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return BoardRequestDto 게시글 정보
     */
    BoardRequestDto getGalleryBoard(String boardId);
}
