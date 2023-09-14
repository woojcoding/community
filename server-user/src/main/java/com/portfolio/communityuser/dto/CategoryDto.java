package com.portfolio.communityuser.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 정보를 담은 DTO 클래스입니다.
 */
@Getter
@Setter
public class CategoryDto {

    /**
     * 카테고리 Id
     */
    private Integer categoryId;

    /**
     * 카테고리 이름
     */
    private String name;
}
