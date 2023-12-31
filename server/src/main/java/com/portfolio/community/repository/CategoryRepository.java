package com.portfolio.community.repository;

import com.portfolio.community.dto.CategoryDto;
import com.portfolio.community.type.BoardType;
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
    public List<CategoryDto> getCategoryList(BoardType type) {
        return categoryMapper.getCategoryList(type);
    }
}
