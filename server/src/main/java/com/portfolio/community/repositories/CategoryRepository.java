package com.portfolio.community.repositories;

import com.portfolio.community.dtos.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Category repository.
 */
@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    /**
     * 카테고리에 대한 db조작을 하는 인터페이스 의존성 주입
     */
    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 목록을 반환해주는 메서드
     *
     * @return List<Category> 카테고리 List
     */
    public List<CategoryDto> getCategoryList(String type) {
        return categoryMapper.getCategoryList(type);
    }
}
