package com.portfolio.communityuser.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.communityuser.controller.ApiResult;
import com.portfolio.communityuser.controller.ApiStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 스프링 시큐리티에서 발생하는 에러를 처리하기 위한 유틸입니다.
 */
@Component
@RequiredArgsConstructor
public class ErrorResponder {
    /**
     * 다국어 메시지 처리를 지원하는 messageSource 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 에러를 response에 담아주는 메서드
     *
     * @param response the response
     * @param status   the status
     * @param error    the error
     * @throws IOException the io exception
     */
    public void sendErrorResponse(
            HttpServletResponse response,
            HttpStatus status,
            String error
    ) throws IOException {
        String message =
                messageSource.getMessage(error, null,
                        LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.FAIL)
                .message(message)
                .build();

        // ApiResult를 JSON 형식으로 변환하여 response의 본문에 추가
        ObjectMapper objectMapper = new ObjectMapper();

        String apiResultJson = objectMapper.writeValueAsString(apiResult);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(apiResultJson);
    }
}
