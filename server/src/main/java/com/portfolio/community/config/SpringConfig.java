package com.portfolio.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * HiddenHttpMethodFilter를 설정하기 위한 Spring 구성 클래스
 * 이 클래스는 HiddenHttpMethodFilter를 등록하여 HTML 폼을 통해 DELETE, PUT, PATCH 등과 같은 HTTP 메서드를 간접적으로 지원
 * 이를 통해 DELETE와 PUT 요청을 처리할 수 있게 됨
 */
@Configuration
public class SpringConfig {

    /**
     * HiddenHttpMethodFilter 빈을 생성하고 구성
     *
     * @return HiddenHttpMethodFilter 빈 객체
     */
    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return hiddenHttpMethodFilter;
    }
}
