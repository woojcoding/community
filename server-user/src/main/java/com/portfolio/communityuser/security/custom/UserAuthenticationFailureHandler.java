package com.portfolio.communityuser.security.custom;

import com.portfolio.communityuser.util.ErrorResponder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationFailure를 처리하는 커스텀 핸들러 입니다.
 */
@Component
@RequiredArgsConstructor
public class UserAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    /**
     *  에러를 response에 담아 처리해주는 유틸 ErrorResponder 의존성 주입
     */
    private final ErrorResponder errorResponder;

    /**
     * 발생한 에러를 ErrorResponder에게 위임합니다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param exception AuthenticationException
     * @throws IOException
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        errorResponder.sendErrorResponse(
                response, HttpStatus.UNAUTHORIZED, "authentication.fail"
        );
    }
}
