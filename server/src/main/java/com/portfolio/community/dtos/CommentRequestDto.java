package com.portfolio.community.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 댓글 작성 및 수정에 사용되는 DTO 클래스입니다.
 *
 * 이 DTO 클래스는 댓글 작성에 필요한 정보를 담고 있으며, 다음과 같은 필드를 가지고 있습니다:
 * - Integer commentId: 댓글 아이디
 * - String content: 댓글 본문 내용
 * - String writer: 작성자
 * - String createdAt: 작성 일자
 */
@Getter
@Setter
public class CommentRequestDto {

    private Integer commentId; // 댓글 Id

    private String adminId; // 어드민 Id

    private Integer boardId; // 게시글 Id

    @NotBlank
    private String content; // 본문 내용

    private String writer; // 작성자

    private String createdAt; // 작성 일자
}
