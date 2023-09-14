package com.portfolio.community.repository;

import com.portfolio.community.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Gallery board mapper.
 */
@Mapper
public interface GalleryBoardMapper {

    /**
     * 갤러리 글을 저장하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    void postGalleryBoard(BoardDto boardDto);

    /**
     * 갤러리 글을 업데이트 하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    void updateGalleryBoard(BoardDto boardDto);

    /**
     * 업데이트를 위해 게시글의 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return boardDto 게시글 정보
     */
    BoardDto getGalleryBoard(int boardId);
}
