package com.portfolio.community.utils;

import com.portfolio.community.dtos.AdminDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 인증에 관한 유틸
 */
public class AuthenticationUtil {

    /**
     * SecurityContextHoler에서 현재 인증된 어드민의 id를 가져오는 메서드
     *
     * @return the admin id
     */
    public static int getAdminId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return ((AdminDto) authentication.getPrincipal()).getAdminId();
    }
}
