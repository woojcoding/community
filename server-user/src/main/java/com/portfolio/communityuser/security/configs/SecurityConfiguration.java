package com.portfolio.communityuser.security.configs;

import com.portfolio.communityuser.security.custom.DelegatedAuthenticationEntryPoint;
import com.portfolio.communityuser.security.custom.UserAccessDeniedHandler;
import com.portfolio.communityuser.security.custom.UserAuthenticationFailureHandler;
import com.portfolio.communityuser.security.filters.JwtAuthenticationFilter;
import com.portfolio.communityuser.security.filters.JwtVerificationFilter;
import com.portfolio.communityuser.utils.ErrorResponder;
import com.portfolio.communityuser.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * 스프링 시큐리티를 위한 설정파일
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
    /**
     * 필터에서 사용하는 JwtUtil 의존성 주입
     */
    private final JwtUtil jwtUtil;

    /**
     * 필터에서 사용하는 MessageSource 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 핸들러와 엔트리포인트에서 사용하는 ErrorResponder 의존성 주입
     */
    private final ErrorResponder errorResponder;

    /**
     * 시큐리티 필터 체인
     * CSRF 보호 비활성화
     * X-Frame-Options 비활성화
     * Cross-Origin Resource Sharing (CORS) 설정 기본값 적용
     * 세션 관리 정책 설정 (STATELESS로 세션 생성 비활성화)
     * HTTP 기본 인증 비활성화
     * 폼 기반 로그인 비활성화
     * 커스텀 필터를 적용하는 CustomFilterConfigurer 적용
     * 예외 처리 설정 (DelegatedAuthenticationEntryPoint와 UserAccessDeniedHandler를 사용하여 에러 핸들링)
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
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .apply(new CustomFilterConfigurer())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new DelegatedAuthenticationEntryPoint(errorResponder))
                .accessDeniedHandler(new UserAccessDeniedHandler(errorResponder))
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                        .antMatchers(HttpMethod.GET, "/**").permitAll()
                        .anyRequest().authenticated());

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

    /**
     * CORS(Cross-Origin Resource Sharing) 구성을 설정하는 메서드입니다.
     * <p>
     * 허용할 오리진(origin) 리스트를 설정
     * 허용할 HTTP 메서드 리스트를 설정
     * 인증 정보를 서로 주고받을 수 있도록 허용하는지 여부를 설정
     * 허용할 요청 헤더(header)를 설정
     * 브라우저로 반환될 응답 헤더(header)를 설정
     * "/**" 패턴에 대한 CORS 구성 정보를 등록
     * 생성된 CORS 구성 정보를 가지고 있는 UrlBasedCorsConfigurationSource 객체를 반환
     *
     * @return CORS 구성 정보를 가지고 있는 CorsConfigurationSource 객체
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.setExposedHeaders(
                Arrays.asList("Authorization", "Content-Disposition")
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /**
     * 사용자 정의 필터를 구성하는 클래스입니다.
     */
    public class CustomFilterConfigurer extends
            AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

        /**
         * HttpSecurity 빌더를 통해 필터를 구성합니다.
         *
         * @param builder HttpSecurity 빌더
         * @throws Exception 예외가 발생할 경우
         */
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);

            // JWT 인증 필터를 생성
            JwtAuthenticationFilter jwtAuthenticationFilter =
                    new JwtAuthenticationFilter(
                            authenticationManager, jwtUtil, messageSource
                    );

            // JWT 검증 필터를 생성
            JwtVerificationFilter jwtVerificationFilter =
                    new JwtVerificationFilter(jwtUtil);

            // 로그인 URL을 설정
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");

            // 인증 실패 핸들러를 설정
            jwtAuthenticationFilter.setAuthenticationFailureHandler(
                    new UserAuthenticationFailureHandler(errorResponder)
            );

            //인증 필터를 HttpSecurity 빌더에 추가
            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(
                            jwtVerificationFilter,
                            JwtAuthenticationFilter.class
                    );
        }
    }
}
