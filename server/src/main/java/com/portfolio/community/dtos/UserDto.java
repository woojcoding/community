package com.portfolio.community.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원에 대한 정보를 담고 있는 UserDto입니다.
 */
@Getter
@Setter
public class UserDto {

    private String accountId;

    private String password;

    private String name;
}
