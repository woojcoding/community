package com.portfolio.communityuser.controllers;

import com.portfolio.communityuser.dtos.IdCheckDto;
import com.portfolio.communityuser.dtos.UserDto;
import com.portfolio.communityuser.exceptions.DuplicatedAccountIdException;
import com.portfolio.communityuser.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    /**
     * 유저에 대한 로직을 처리하는 userService 의존성 주입
     */
    private final UserService userService;

    /**
     * 메세지를 관리하는 messageSource 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 회원 가입 요청을 하는 메서드
     * 회원아이디가 중복된다면 DuplicateAccountIdException 오류를 반환
     *
     * @param userDto 유저 정보
     * @return ResponseEntity<ApiResult>
     */
    @PostMapping
    public ResponseEntity<ApiResult> signUp(
            @Valid @RequestBody UserDto userDto
    ) {
        // 회원아이디 중복 검증
        if (userService.verifyExistAccountId(userDto.getAccountId())) {
            throw new DuplicatedAccountIdException();
        }

        // 회원 가입
        userService.signUp(userDto);

        String message =
                messageSource.getMessage("sign.up.success",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 아이디 중복 체크를 하기 위한 메서드
     *
     * @param idCheckDto 아이디
     * @return ResponseEntity<ApiResult>
     */
    @GetMapping
    public ResponseEntity<ApiResult> confirmAccountIdDuplication(
            @Valid @ModelAttribute IdCheckDto idCheckDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessageBuilder = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessageBuilder.append(fieldError.getDefaultMessage());
                errorMessageBuilder.append("\n");
            }

            String combinedErrorMessage = errorMessageBuilder.toString();

            ApiResult apiResult = ApiResult.builder()
                    .status(ApiStatus.FAIL)
                    .message(combinedErrorMessage)
                    .build();

            return ResponseEntity
                    .badRequest()
                    .body(apiResult);
        }

        boolean isDuplicated =
                userService.verifyExistAccountId(idCheckDto.getAccountId());

        String message = "";

        if (isDuplicated) {
            message = messageSource.getMessage("duplicated.account.id.exception",
                    null, LocaleContextHolder.getLocale());
        } else {
            message = messageSource.getMessage("account.id.can.use",
                    null, LocaleContextHolder.getLocale());
        }

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .data(isDuplicated)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }
}
