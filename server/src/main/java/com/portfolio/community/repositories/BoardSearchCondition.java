package com.portfolio.community.repositories;

import com.portfolio.community.enums.BoardType;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 검색 조건 클래스
 */
@Getter
@Setter
public class BoardSearchCondition {

    /**
     * 현재 페이지 번호
     */
    private int pageNum = 1;

    /**
     * 검색 시작일
     */
    private String startDate;

    /**
     * 검색 종료일
     */
    private String endDate;

    /**
     * 카테고리
     */
    private String category;

    /**
     * 검색 키워드
     */
    private String keyword;

    /**
     * 페이지 사이즈 (한 페이지에 보여줄 게시글 수)
     */
    private int pageSize = 10;

    /**
     * 정렬 조건 (예: "created_at")
     */
    private String orderOption;

    /**
     * 정렬 방식 (내림차순: "DESC", 오름차순: "ASC")
     */
    private String sort;

    /**
     * 페이지네이션 오프셋 (DB에서 검색 시작 위치)
     */
    private int offSet;

    /**
     * 게시글 타입
     */
    private BoardType type;
}
