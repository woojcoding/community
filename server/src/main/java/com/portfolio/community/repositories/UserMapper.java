package com.portfolio.community.repositories;

import com.portfolio.community.dtos.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * 마이바티스 user 매퍼
 */
@Mapper
public interface UserMapper {

    /**
     * 회원 아이디 중복 여부를 위해 AccountId로 회원을 조회하는 메서드
     *
     * @param accountId 회원 아이디
     * @return Optional<UserDto>
     */
    Optional<UserDto> findUserByAccountId(String accountId);

    /**
     * 회원 가입 시 회원 정보를 DB에 반영하는 메서드
     *
     * @param userDto userDto
     */
    void signUp(UserDto userDto);
}
