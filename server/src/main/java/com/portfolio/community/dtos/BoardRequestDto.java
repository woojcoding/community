package com.portfolio.community.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 게시글 등록에 사용되는 DTO 클래스입니다.
 *
 * 이 DTO 클래스는 게시글 등록에 필요한 정보를 담고 있으며, 다음과 같은 필드들을 가지고 있습니다:
 * - Integer boardId: 반환되는 게시글 ID
 * - int userId: 작성자 ID
 * - String categoryId: 카테고리 ID
 * - String password: 비밀번호
 * - String title: 제목
 * - String content: 내용
 * - String status: 상태
 * - String answer: 답변
 * - MultipartFile[] files: 파일들
 */
@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {

    private Integer boardId; // 반환되는 게시글 Id

    private int userId; // 게시글 작성자 Id

    private String categoryId; // 카테고리 Id

    private Integer boardInfoId; // 보드정보 Id

    private String type; // 타입

    private String title; // 제목

    private String content; // 내용

    private String status; // 상태

    private String answer; // 답변

    private  MultipartFile[] files; // 파일들
}
