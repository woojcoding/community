package com.portfolio.community.repositories;

import org.apache.ibatis.annotations.Mapper;

/**
 * The interface Board info mapper.
 */
@Mapper
public interface BoardInfoMapper {

    /**
     * type을 기반으로 boardInfoId를 가져오는 메서드
     *
     * @param type 게시글 타입
     * @return boardInfoId
     */
    int getBoardInfoId(String type);
}
