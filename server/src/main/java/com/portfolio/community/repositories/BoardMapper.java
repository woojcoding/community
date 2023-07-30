package com.portfolio.community.repositories;

import com.portfolio.community.dtos.BoardRequestDto;
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
     * 게시글 목록 조회에서 공지사항에서는 알림글 정보들을 List로 가져오는 메서드
     *
     * @return 알림글 List
     */
    List<BoardResponseDto> getNotificationList();

    /**
     * 게시글을 저장하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    void postBoard(BoardRequestDto boardRequestDto);

    /**
     * 업데이트를 위해 게시글의 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return BoardRequestDto 게시글 정보
     */
    BoardRequestDto getBoard(String boardId);


    /**
     * 게시글을 업데이트 하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    void updateBoard(BoardRequestDto boardRequestDto);
}
