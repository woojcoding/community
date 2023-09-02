package com.portfolio.community.controllers;

import com.portfolio.community.dtos.AdminDto;
import com.portfolio.community.exceptions.DuplicateAccountIdException;
import com.portfolio.community.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Admin controller.
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
     * @param adminDto 어드민 정보
     */
    @PostMapping("/signup")
    public void signUp(@RequestBody AdminDto adminDto) {
        if (adminService.verifyExistAccountId(adminDto.getAccountId())) {
            throw new DuplicateAccountIdException();
        }
        adminService.signUp(adminDto);
    }
}
