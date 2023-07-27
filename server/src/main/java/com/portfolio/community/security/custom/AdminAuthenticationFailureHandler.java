package com.portfolio.community.security.custom;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Admin의 로그인 인증 실패시 처리하는 핸들러
 */
public class AdminAuthenticationFailureHandler
        implements AuthenticationFailureHandler {
    /**
     * 인증에 실패한다면 다시 로그인 폼으로 보내는 메서드
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param exception AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        response.sendRedirect("admin/login");
    }
}

