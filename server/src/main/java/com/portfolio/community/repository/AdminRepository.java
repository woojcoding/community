package com.portfolio.community.repository;

import com.portfolio.community.dto.AdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * admin 저장소
 */
@Repository
@RequiredArgsConstructor
public class AdminRepository {

    /**
     * admin매퍼 의존성 주입
     */
    private final AdminMapper userMapper;

    /**
     * AccountId로 admin을 조회하는 메서드
     *
     * @param accountId admin의 계정아이디
     * @return Optional<UserDto>
     */
    public Optional<AdminDto> findAdminByAccountId(String accountId) {
        return userMapper.findAdminByAccountId(accountId);
    }

    /**
     * admin을 생성하여 DB에 반영하는 메서드
     *
     * @param adminDto userDto
     */
    public void signUp(AdminDto adminDto) {
        userMapper.signUp(adminDto);
    }
}
