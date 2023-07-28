package com.portfolio.community.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 정보를 담은 DTO 클래스입니다.
 *
 * 이 DTO 클래스는 카테고리에 대한 정보를 저장하고 있으며, 다음과 같은 필드들을 가지고 있습니다:
 * - Integer categoryId: 카테고리 ID
 * - String categoryName: 카테고리 이름
 */
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId; // 카테고리 ID

    private String name; // 카테고리 이름
}
