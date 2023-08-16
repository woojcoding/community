package com.portfolio.communityuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Login dto.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
     * 로그인 후 반환되는 유저 이름
     */
    private String name;
}
