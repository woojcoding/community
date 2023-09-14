package com.portfolio.community.service;

import com.portfolio.community.dto.AdminDto;
import com.portfolio.community.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * admin 서비스
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    /**
     * UserRepository 의존성 주입
     */
    private final AdminRepository adminRepository;

    /**
     * 암호화를 처리하는 PasswordEncoder 의존성 주입
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입을 하는 메서드
     *
     * @param adminDto adminDto
     */
    public void signUp(AdminDto adminDto) {
        String encodedPassword = passwordEncoder.encode(adminDto.getPassword());

        adminDto.setPassword(encodedPassword);

        adminRepository.signUp(adminDto);
    }

    /**
     * admin 아이디 중복 여부를 위해 AccountId로 회원을 조회하는 메서드
     *
     * @param accountId 회원 아이디
     * @return the boolean 중복됏다면 true 아니라면 false 반환
     */
    public boolean verifyExistAccountId(String accountId) {
        return adminRepository.findAdminByAccountId(accountId).isPresent();
    }
}
