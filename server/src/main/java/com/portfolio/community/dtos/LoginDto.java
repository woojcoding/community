package com.portfolio.community.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인에 이용되는 Dto입니다.
 */
@Getter
@Setter
public class LoginDto {

    /**
     * 계정 Id
     */
    private String accountId;

    /**
     * 비밀번호
     */
    private String password;
}
