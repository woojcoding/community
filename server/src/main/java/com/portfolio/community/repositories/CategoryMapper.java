package com.portfolio.community.repositories;

import com.portfolio.community.dtos.CategoryDto;
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
    List<CategoryDto> getCategoryList(String type);
}
