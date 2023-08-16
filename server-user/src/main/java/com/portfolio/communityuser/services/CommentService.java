package com.portfolio.communityuser.services;

import com.portfolio.communityuser.dtos.CommentDto;
import com.portfolio.communityuser.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Comment service.
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    /**
     * commentRepository 의존성 주입
     */
    private final CommentRepository commentRepository;

    /**
     * 게시글Id로 댓글들을 가져온ㄴ 메서드
     *
     * @param boardId 게시글 Id
     * @return 댓글 List
     */
    public List<CommentDto> getCommentList(int boardId) {
        return commentRepository.getCommentList(boardId);
    }

    /**
     * 댓글을 저장하는 메서드
     *
     * @param commentDto 게시글 본문을 담은 Dto
     */
    public void postComment(CommentDto commentDto) {
        commentRepository.postComment(commentDto);
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
