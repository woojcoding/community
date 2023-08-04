package com.portfolio.community.services;

import com.portfolio.community.dtos.CommentRequestDto;
import com.portfolio.community.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Comment service.
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 게시글Id로 댓글들을 가져온ㄴ 메서드
     *
     * @param boardId 게시글 Id
     * @return 댓글 List
     */
    public List<CommentRequestDto> getCommentList(int boardId) {
        return commentRepository.getCommentList(boardId);
    }

    /**
     * 댓글을 저장하는 메서드
     *
     * @param commentRequestDto 게시글 본문을 담은 Dto
     */
    public void postComment(CommentRequestDto commentRequestDto) {
        commentRepository.postComment(commentRequestDto);
    }

    /**
     *  댓글을 삭제하는 메서드
     *
     * @param commentId 댓글 Id
     */
    public void deleteComment(int commentId) {
        commentRepository.deleteComment(commentId);
    }
}
