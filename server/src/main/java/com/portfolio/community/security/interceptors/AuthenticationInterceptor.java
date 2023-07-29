package com.portfolio.community.security.interceptors;

import com.portfolio.community.dtos.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Authentication interceptor.
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    /**
     * securityContextHoler에서 가져온 Authentication에서 userId를 추출하여
     * request에 userId를 지정해줌으로써 게시글 작성자 아이디를 지정할 수 있습니다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/admin/board")) {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            int userId = ((UserDto) authentication.getPrincipal()).getUserId();

           request.setAttribute("userId", userId);
        }

        return true;
    }
}
