package com.portfolio.community.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * The type Board info repository.
 */
@Repository
@RequiredArgsConstructor
public class BoardInfoRepository {

    /**
     * boardMapper 의존성 주입
     */
    private final BoardInfoMapper boardInfoMapper;

    /**
     * type을 기반으로 boardInfoId를 가져오는 메서드
     *
     * @param type 게시글 타입
     * @return boardInfoId
     */
    public int getBoardInfoId(String type) {
        return boardInfoMapper.getBoardInfoId(type);
    }
}
