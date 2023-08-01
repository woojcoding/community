package com.portfolio.community.repositories;

import com.portfolio.community.dtos.CommentRequestDto;
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
    public List<CommentRequestDto> getCommentList(String boardId) {
        return commentMapper.getCommentList(boardId);
    }

    /**
     * 댓글을 저장하는 메서드
     *
     * @param commentRequestDto 게시글 본문을 담은 Dto
     */
    public void postComment(CommentRequestDto commentRequestDto) {
        commentMapper.postComment(commentRequestDto);
    }

    /**
     *  댓글을 삭제하는 메서드
     *
     * @param commentId 댓글 Id
     */
    public void deleteComment(int commentId) {
        commentMapper.deleteComment(commentId);
    }
}
