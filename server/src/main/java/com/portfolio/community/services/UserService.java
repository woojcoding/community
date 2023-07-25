package com.portfolio.community.services;

import com.portfolio.community.dtos.UserDto;
import com.portfolio.community.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * user 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입을 하는 메서드
     *
     * @param userDto the user dto
     */
    public void signUp(UserDto userDto) {
        userRepository.signUp(userDto);
    }

    /**
     * 회원 아이디 중복 여부를 위해 AccountId로 회원을 조회하는 메서드
     *
     * @param accountId 회원 아이디
     * @return the boolean 중복됏다면 true 아니라면 false 반환
     */
    public boolean verifyExistAccountId(String accountId) {
        return userRepository.findUserByAccountId(accountId).isPresent();
    }
}
