package com.portfolio.communityuser.security.custom;

import com.portfolio.communityuser.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *  커스텀한 UserDetailsService 입니다.
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    /**
     *  User에 대한 정보가 있는 UserRepository 의존성 주입
     */
    private final UserRepository userRepository;

    /**
     * 계정의 이름으로 UserDetails를 반환하는 메서드
     *
     * @param accountId 사용자 계정명
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String accountId)
            throws UsernameNotFoundException {
        return userRepository.findUserByAccountId(accountId).get();
    }
}
