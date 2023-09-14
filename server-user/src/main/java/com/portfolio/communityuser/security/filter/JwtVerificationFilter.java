package com.portfolio.communityuser.security.filter;

import com.portfolio.communityuser.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Jwt 토큰을 검증하는 필터
 */
@Component
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    /**
     * jwt토큰에 대한 로직을 처리하는 jwtUtil 의존성 주입
     */
    private final JwtUtil jwtUtil;

    /**
     * 토큰을 해독하여 accountId를 securityContextHolder에 저장하는 메서드
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (!Objects.isNull(authorization)) {
            try {
                String accessToken = authorization.replace("Bearer ", "");

                String accountId = jwtUtil.decode(accessToken);

                setAuthenticationToContext(accountId);
            } catch (Exception e) {
                request.setAttribute("exception", e);
            }

            filterChain.doFilter(request, response);
        }
    }

    /**
     * access token이 없을 경우 true를 반환하여 filter를 건너뛴다.
     *
     * @param request current HTTP request
     * @return boolean
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    /**
     * Authentication을 securityContextHolder에 저장하는 메서드
     *
     * @param accountId
     */
    private void setAuthenticationToContext(String accountId) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        accountId,
                        null,
                        null
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
