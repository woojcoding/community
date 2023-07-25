package com.portfolio.community.exceptions;

import com.portfolio.community.controllers.ApiResult;
import com.portfolio.community.controllers.ApiStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 전역 예외 처리를 담당하는 클래스입니다.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    /**
     * 다국어 메시지 처리를 지원하는 messageSource 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 회원의 아이디가 중복되었을 때 예외를 처리하는 메서드
     *
     * @return ResponseEntity<ApiResult>
     */
    @ExceptionHandler(DuplicateAccountIdException.class)
    public ResponseEntity<ApiResult> handleDuplicateAccountIdException() {
        String message =
                messageSource.getMessage("duplicate.account.id.exception",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.FAIL)
                .message(message)
                .build();

        return ResponseEntity
                .badRequest()
                .body(apiResult);
    }
}
