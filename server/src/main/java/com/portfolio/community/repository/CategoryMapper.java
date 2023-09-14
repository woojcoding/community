package com.portfolio.community.repository;

import com.portfolio.community.dto.CategoryDto;
import com.portfolio.community.type.BoardType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * The interface Category mapper.
 */
@Mapper
public interface CategoryMapper {

    /**
     * 카테고리 목록을 반환해주는 메서드
     *
     * @return List<Category> 카테고리 List
     */
    List<CategoryDto> getCategoryList(BoardType type);
}
