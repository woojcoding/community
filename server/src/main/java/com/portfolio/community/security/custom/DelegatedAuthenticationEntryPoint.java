package com.portfolio.community.security.custom;

import com.portfolio.community.utils.ErrorResponder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DelegatedAuthenticationEntryPoint
        implements AuthenticationEntryPoint {
    /**
     *  에러를 response에 담아 처리해주는 유틸 ErrorResponder 의존성 주입
     */
    private final ErrorResponder errorResponder;

    /**
     * 발생한 예외를 ErrorResponder에게 위임합니다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authException AuthenticationException
     * @throws IOException
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        errorResponder.sendErrorResponse(
                response, HttpStatus.UNAUTHORIZED, "authentication.fail"
        );
    }
}
