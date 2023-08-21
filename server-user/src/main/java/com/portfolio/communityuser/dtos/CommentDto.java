package com.portfolio.communityuser.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 댓글 작성 및 수정에 사용되는 DTO 클래스입니다.
 */
@Getter
@Setter
public class CommentDto {

    /**
     * 댓글 ID
     */
    private Integer commentId;

    /**
     * 유저 ID
     */
    private String userId;

    /**
     * 어드민 ID
     */
    private String adminId;

    /**
     * 게시글 ID
     */
    private Integer boardId;

    /**
     * 본문 내용
     */
    @NotBlank(message = "{notBlank.commentDto.content}")
    private String content;

    /**
     * 작성자
     */
    private String writer;

    /**
     * 작성 일자
     */
    private String createdAt;
}
