package com.portfolio.communityuser.repositories;

import com.portfolio.communityuser.dtos.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Comment repository.
 */
@Repository
@RequiredArgsConstructor
public class CommentRepository {

    /**
     * 댓글에 대한 db를 조작하는 commentMapper 의존성 주입
     */
    private final CommentMapper commentMapper;

    /**
     * 댓글 목록을 반환해주는 메서드
     *
     * @param boardId 게시글 Id
     * @return List<CommentResponseDto>  댓글 List
     */
    public List<CommentDto> getCommentList(int boardId) {
        return commentMapper.getCommentList(boardId);
    }

    /**
     * 댓글을 저장하는 메서드
     *
     * @param commentDto 게시글 본문을 담은 Dto
     */
    public void postComment(CommentDto commentDto) {
        commentMapper.postComment(commentDto);
    }

    /**
     *  댓글을 삭제하는 메서드
     *
     * @param commentId 댓글 Id
     */
    public void deleteComment(int commentId) {
        commentMapper.deleteComment(commentId);
    }

    /**
     * 댓글을 조회하는 메서드
     *
     * @param commentId 댓글Id
     * @return CommentDto
     */
    public CommentDto getComment(int commentId) {
        return commentMapper.getComment(commentId);
    }
}
