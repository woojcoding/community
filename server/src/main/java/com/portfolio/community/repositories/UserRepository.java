package com.portfolio.community.repositories;

import com.portfolio.community.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * user 저장소
 */
@Repository
@RequiredArgsConstructor
public class UserRepository {
    /**
     * user 매퍼 의존성 주입
     */
    private final UserMapper userMapper;

    /**
     * 회원 아이디 중복 여부를 위해 AccountId로 회원을 조회하는 메서드
     *
     * @param accountId 회원 아이디
     * @return Optional<UserDto>
     */
    public Optional<UserDto> findUserByAccountId(String accountId) {
        return userMapper.findUserByAccountId(accountId);
    }

    /**
     * 회원 가입 시 회원 정보를 DB에 반영하는 메서드
     *
     * @param userDto userDto
     */
    public void signUp(UserDto userDto) {
        userMapper.signUp(userDto);
    }
}
