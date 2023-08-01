package com.portfolio.community.repositories;

import com.portfolio.community.dtos.AdminDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * 마이바티스 admin 매퍼
 */
@Mapper
public interface AdminMapper {

    /**
     * admin 아이디 중복 여부를 위해 AccountId로 회원을 조회하는 메서드
     *
     * @param accountId admin 계정 아이디
     * @return Optional<UserDto> optional
     */
    Optional<AdminDto> findAdminByAccountId(String accountId);

    /**
     * admin 회원가입하는 메서드
     *
     * @param adminDto 어드민 정보
     */
    void signUp(AdminDto adminDto);
}
