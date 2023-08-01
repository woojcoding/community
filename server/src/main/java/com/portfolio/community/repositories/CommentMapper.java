package com.portfolio.community.repositories;

import com.portfolio.community.dtos.CommentRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * The interface Comment mapper.
 */
@Mapper
public interface CommentMapper {

    /**
     * 댓글 목록을 반환해주는 메서드
     *
     * @param boardId 게시글 Id
     * @return List<CommentResponseDto>  댓글 List
     */
    List<CommentRequestDto> getCommentList(String boardId);

    /**
     * 댓글을 저장하는 메서드
     *
     * @param commentRequestDto 게시글 본문을 담은 Dto
     */
    void postComment(CommentRequestDto commentRequestDto);

    /**
     *  댓글을 삭제하는 메서드
     *
     * @param commentId 댓글 Id
     */
    void deleteComment(int commentId);
}
