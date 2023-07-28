package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ADMIN의 요청을 받아들이는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    /**
     * 로그인 폼을 가져오는 메서드.
     *
     * @param loginDto       로그인 정보 DTO
     * @param model          Model
     * @param authentication Authentication
     * @return 로그인 폼
     */
    @GetMapping("/login")
    public String getLoginForm(
            LoginDto loginDto,
            Model model,
            Authentication authentication
    ) {
        // 로그인이 이미 되어있으므로 admin/home으로 redirect
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            return "redirect:/admin/home";
        }

        model.addAttribute("loginDto", loginDto);

        return "admin/views/loginForm";
    }

    /**
     * 로그인 성공 후 홈 폼을 가져오는 메서드
     *
     * @param model Model
     * @return 홈 폼
     */
    @GetMapping("/home")
    public String getHomeForm(Model model) {
        return "admin/views/home";
    }
}
