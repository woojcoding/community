package com.portfolio.community.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인에 이용되는 Dto입니다.
 */
@Getter
@Setter
public class LoginDto {

    private String accountId;

    private String password;
}
