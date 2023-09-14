package com.portfolio.community.security.config;

import com.portfolio.community.security.custom.AdminAuthenticationFailureHandler;
import com.portfolio.community.security.custom.AdminAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 스프링 시큐리티를 위한 설정파일
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * 시큐리티 필터 체인
     * CSRF 보호 비활성화
     * X-Frame-Options 비활성화
     * Cross-Origin Resource Sharing (CORS) 설정 기본값 적용
     * 세션 관리 정책 설정 (IF_REQUIRED로 세션을 이용한 ADMIN 인증)
     * HTTP 기본 인증 비활성화
     * 폼 기반 로그인 활성화
     * 로그아웃 지정
     * HTTP 요청 권한 설정
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .cors(Customizer.withDefaults())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .httpBasic().disable();

        http.formLogin()
                .loginPage("/admin/login")
                .usernameParameter("accountId")
                .passwordParameter("password")
                .successHandler(new AdminAuthenticationSuccessHandler())
                .failureHandler(new AdminAuthenticationFailureHandler())
                .permitAll();

        http.logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .deleteCookies("JSESSIONID");

        http.authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/admin/login").permitAll()
                        .antMatchers("/admin/backdoor/signup").permitAll()
                        .antMatchers("/admin/**").authenticated());

        return http.build();
    }

    /**
     * 패스워드 인코더
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
