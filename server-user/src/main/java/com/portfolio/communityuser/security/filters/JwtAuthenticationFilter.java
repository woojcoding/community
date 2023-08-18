package com.portfolio.communityuser.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.communityuser.controllers.ApiResult;
import com.portfolio.communityuser.controllers.ApiStatus;
import com.portfolio.communityuser.dtos.LoginDto;
import com.portfolio.communityuser.dtos.UserDto;
import com.portfolio.communityuser.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 회원 인증 후 로그인에 성공한다면 jwt를 부여해주는 필터입니다.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    /**
     * 인증을 해주는 AuthenticationManager 의존성 주입
     */
    private final AuthenticationManager authenticationManager;

    /**
     * 토큰과 관련된 유틸인 JwtUtil 의존성 주입
     */
    private final JwtUtil jwtUtil;

    /**
     * 다국어 메시지 처리를 지원하는 messageSource 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * // LoginDto -> authenticationToken -> authenticationManager에 인증 위임
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws AuthenticationException
     */
    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();

        LoginDto loginDto =
                objectMapper.readValue(request.getInputStream(), LoginDto.class);

        // authenticationToken을 만들어 authenticationManager에 인증을 위힘
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getAccountId(), loginDto.getPassword()
                );

        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 인증에 성공했을 때 호출하는 메서드
     *
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @param chain      FilterChain
     * @param authResult 인증 정보
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        UserDto userDto = (UserDto) authResult.getPrincipal();

        String accessToken = jwtUtil.encode(userDto.getAccountId());

        String message =
                messageSource.getMessage(
                        "login.success", null,
                        LocaleContextHolder.getLocale()
                );

        LoginDto loginResultDto = LoginDto.builder()
                .accountId(userDto.getAccountId())
                .name(userDto.getName())
                .accessToken("Bearer " + accessToken)
                .build();

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .data(loginResultDto)
                .build();

        // ApiResult를 JSON 형식으로 변환하여 response의 본문에 추가

        ObjectMapper objectMapper = new ObjectMapper();

        String apiResultJson = objectMapper.writeValueAsString(apiResult);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(apiResultJson);
    }
}
