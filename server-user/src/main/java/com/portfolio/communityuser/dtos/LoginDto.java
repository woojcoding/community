package com.portfolio.communityuser.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Login dto.
 */
@Getter
@Setter
@Builder
public class LoginDto {
    /**
     * 계정 Id
     */
    private String accountId;

    /**
     *  비밀번호
     */
    private String password;

    /**
     * 로그인 후 반환되는 엑세스토큰
     */
    private String accessToken;

    /**
     * 기본 생성자
     */
    private LoginDto() {
    }
}
