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
        // 비밀번호가 틀렸을 때 알림을 보여줄 스크립트를 작성
        String errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
        String script = "<script>alert('" + errorMessage + "');window.location.href='/admin/login';</script>";

        // 스크립트를 응답으로 전송
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(script);
    }
}
