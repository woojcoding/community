package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.AdminDto;
import com.portfolio.community.exceptions.DuplicateAccountIdException;
import com.portfolio.community.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ADMIN의 가입을 위해 만들어둔 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/backdoor")
public class AdminController {

    /**
     * admin에 대한 로직을 처리하는 AdminService 의존성 주입
     */
    private final AdminService adminService;

    /**
     * admin 가입을 하는 메서드
     *
     * @param adminDto
     */
    @PostMapping("/signup")
    public void signUp(@RequestBody AdminDto adminDto) {
        if (adminService.verifyExistAccountId(adminDto.getAccountId())) {
            throw new DuplicateAccountIdException();
        }
        adminService.signUp(adminDto);
    }
}
