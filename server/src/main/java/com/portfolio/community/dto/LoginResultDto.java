package com.portfolio.community.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 성공 후 반환되는 DTO입니다.
 */
@Getter
@Builder
public class LoginResultDto {

    private int userId;

    private String accessToken;
}
