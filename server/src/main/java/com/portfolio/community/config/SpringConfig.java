package com.portfolio.community.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring 구성 클래스
 * 이 클래스는 HiddenHttpMethodFilter를 등록하여 HTML 폼을 통해 DELETE, PUT, PATCH 등과 같은 HTTP 메서드를 간접적으로 지원
 * 이를 통해 DELETE와 PUT 요청을 처리할 수 있게 됨
 *
 * 리소스 핸들러의 파일 경로를 추가
 *
 */
@Configuration
public class SpringConfig implements WebMvcConfigurer {

    @Value("${file.dir}")
    private String fileDir;

    public SpringConfig(@Value("${file.dir}") String fileDir) {
        this.fileDir = fileDir;
    }

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

    /**
     * 썸네일을 가져오기 위해 리소스 핸들러의 경로에 파일의 경로를 추가해줌
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/thumbnails/**")
                .addResourceLocations("file:" + fileDir + "/");
    }
}
