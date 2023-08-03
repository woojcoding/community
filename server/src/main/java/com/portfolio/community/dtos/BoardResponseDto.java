package com.portfolio.community.dtos;

import com.portfolio.community.enums.BoardType;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 목록 조회에서 사용되는 개별 게시글 정보를 나타내는 DTO 클래스입니다.
 *
 * 이 DTO 클래스는 개별 게시글의 정보를 담고 있으며, 다음과 같은 필드들을 가지고 있습니다:
 * - Integer boardId: 게시글 ID
 * - String categoryName: 카테고리명
 * - String writer: 작성자
 * - String title: 제목
 * - String answer: 답변
 * - String views: 조회수
 * - String createdAt: 작성일
 * - boolean notificationFlag: 알림글 여부
 * - boolean secretFlag: 비밀글 여부
 * - BoardType type: 타입
 * - String thumbnailName: 썸네일 이름
 * - int imageCount: 이미지 개수
 * - int commentCount: 댓글 개수
 * - boolean isAttached: 파일 첨부 여부
 */
@Getter
@Setter
public class BoardResponseDto {

    private Integer boardId; // 게시글 ID

    private String categoryName; // 카테고리명

    private String writer; // 작성자

    private String title; // 제목

    private String answer; //

    private String views; // 조회수

    private String createdAt; // 작성일

    private boolean notificationFlag; // 알림글 여부

    private boolean secretFlag; // 비밀글 여부

    private BoardType type; // 타입

    private String thumbnailName; // 썸네일 이름

    private int imageCount; // 이미지 개수

    private int commentCount; // 댓글 개수

    private boolean isAttached; //파일 첨부 여부
}
