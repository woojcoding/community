package com.portfolio.community.repositories;

import com.portfolio.community.dtos.BoardResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * The interface Board mapper.
 */
@Mapper
public interface BoardMapper {

    /**
     * 게시글 목록 조회에서  검색 조건에 따라 게시글 정보들을 List로 가져오는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return List<BoardResponseDto>           게시글 정보 List
     */
    List<BoardResponseDto> getBoardList(BoardSearchCondition boardSearchCondition);

    /**
     * 게시글 목록 조회에서  검색 조건에 따라 검색 되는 게시글의 총 수
     *
     * @param boardSearchCondition 검색 조건
     * @return 게시글 조회 건 수
     */
    int getTotalBoardCount(BoardSearchCondition boardSearchCondition);

    /**
     * db에서 게시글을 삭제하는 메서드
     *
     * @param boardId
     */
    void deleteBoard(int boardId);
}
