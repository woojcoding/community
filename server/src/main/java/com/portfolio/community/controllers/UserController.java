package com.portfolio.community.controllers;

import com.portfolio.community.dtos.UserDto;
import com.portfolio.community.exceptions.DuplicateAccountIdException;
import com.portfolio.community.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User에 대한 API 요청을 받는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    /**
     * User와 관련된 로직을 처리하는 userService 의존성 주입
     */
    private final UserService userService;

    /**
     * 다국어 메시지 처리를 지원하는 messageSource 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 회원 가입 요청을 하는 메서드
     * 회원아이디가 중복된다면 DuplicateAccountIdException 오류를 반환
     *
     * @param userDto 유저 정보
     * @return ResponseEntity<ApiResult>
     */
    @PostMapping("/users")
    public ResponseEntity<ApiResult> signUp(@RequestBody UserDto userDto) {
        // 회원아이디 중복 검증
        if (userService.verifyExistAccountId(userDto.getAccountId())) {
            throw new DuplicateAccountIdException();
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
}
