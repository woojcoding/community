package com.portfolio.communityuser.service;

import com.portfolio.communityuser.dto.UserDto;
import com.portfolio.communityuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * user 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * user에 대한 db userRepository 의존성 주입
     */
    private final UserRepository userRepository;

    /**
     * 비밀번호를 암호화하는 passwordEncoder 의존성 주입
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입을 하는 메서드
     *
     * @param userDto the user dto
     */
    public void signUp(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        userDto.setPassword(encodedPassword);

        userRepository.signUp(userDto);
    }

    /**
     * 회원 아이디 중복 여부를 위해 AccountId로 회원을 조회하는 메서드
     *
     * @param accountId 회원 아이디
     * @return the boolean 중복됐다면 true 아니라면 false 반환
     */
    public boolean verifyExistAccountId(String accountId) {
        return userRepository.findUserByAccountId(accountId).isPresent();
    }
}
