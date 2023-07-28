package com.portfolio.community.security.custom;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Admin의 인증 성공시 처리하는 핸들러
 */
public class AdminAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    /**
     * 로그인에 성공 시 /admin/home으로 redirect
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authentication Authenticationng
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        response.sendRedirect("/admin/home");
    }
}

