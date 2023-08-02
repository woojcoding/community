package com.portfolio.community.repositories;

import com.portfolio.community.dtos.BoardRequestDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Help board mapper.
 */
@Mapper
public interface HelpBoardMapper {

    /**
     * 답변을 달아 업데이트 하는 메서드
     *
     * @param boardRequestDto 게시글 정보
     */
    void answerHelpBoard(BoardRequestDto boardRequestDto);

    /**
     * 답변을 위해 게시글의 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return BoardRequestDto 게시글 정보
     */
    BoardRequestDto getHelpBoard(String boardId);
}
