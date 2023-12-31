package com.portfolio.communityuser.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 인증에 관한 유틸
 */
public class AuthenticationUtil {

    /**
     * SecurityContextHoler에서 현재 인증된 유저의 id를 가져오는 메서드
     *
     * @return the userId
     */
    public static String getAccountId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return (String) authentication.getPrincipal();
    }
}
