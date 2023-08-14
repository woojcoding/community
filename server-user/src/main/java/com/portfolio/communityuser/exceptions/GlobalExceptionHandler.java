package com.portfolio.communityuser.exceptions;

import com.portfolio.communityuser.controllers.ApiResult;
import com.portfolio.communityuser.controllers.ApiStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 전역 예외 처리를 담당하는 클래스입니다.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    /**
     * 다국어 메시지 처리를 지원하는 messageSource 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * requestBody의 경우 spring Bean validation에 실패하는 경우 처리해주는 메서드
     *
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity<ApiResult>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }

        String combinedErrorMessage = String.join("\n", errorMessages);

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.FAIL)
                .message(combinedErrorMessage)
                .build();

        return ResponseEntity
                .badRequest()
                .body(apiResult);
    }

    /**
     * 회원의 아이디가 중복되었을 때 예외를 처리하는 메서드
     *
     * @return ResponseEntity<ApiResult>
     */
    @ExceptionHandler(DuplicatedAccountIdException.class)
    public ResponseEntity<ApiResult> handleDuplicatedAccountIdException() {
        String message =
                messageSource.getMessage("duplicated.account.id.exception",
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
