package com.portfolio.community.services;

import com.portfolio.community.dtos.CategoryDto;
import com.portfolio.community.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Category service.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    /**
     * CategoryRepository 의존성 주입
     */
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 목록을 Repository에 요청하기 위한 메서드
     *
     * @return List<Category> 카테고리 List
     */
    public List<CategoryDto> getCategoryList(String type) {
        return categoryRepository.getCategoryList(type);
    }
}
