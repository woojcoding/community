package com.portfolio.community.security.custom;

import com.portfolio.community.utils.ErrorResponder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AccessDenied 를 처리하는 커스텀 핸들러입니다.
 */
@Component
@RequiredArgsConstructor
public class UserAccessDeniedHandler implements AccessDeniedHandler {

    /**
     *  에러를 response에 담아 처리해주는 유틸 ErrorResponder 의존성 주입
     */
    private final ErrorResponder errorResponder;

    /**
     * 발생한 예외를 ErrorResponder에게 위임합니다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param accessDeniedException AccessDeniedException
     * @throws IOException
     */
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        errorResponder.sendErrorResponse(
                response, HttpStatus.FORBIDDEN, "access.denied"
        );
    }
}
