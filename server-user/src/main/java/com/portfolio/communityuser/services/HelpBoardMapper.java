package com.portfolio.communityuser.services;

import com.portfolio.communityuser.dtos.BoardDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Help board mapper.
 */
@Mapper
public interface HelpBoardMapper {

    /**
     * 답변을 달아 업데이트 하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    void updateHelpBoard(BoardDto boardDto);

    /**
     * 게시글의 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return boardDto 게시글 정보
     */
    BoardDto getHelpBoard(String boardId);
}
