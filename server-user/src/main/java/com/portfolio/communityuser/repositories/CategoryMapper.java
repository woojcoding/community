package com.portfolio.communityuser.repositories;

import com.portfolio.communityuser.dtos.CategoryDto;
import com.portfolio.communityuser.enums.BoardType;
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
