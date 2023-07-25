package com.portfolio.community.controllers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *  API 결과를 반환해줄 때 사용해주는 공통 클래스입니다.
 *  {
 *     "status": "",
 *     "message": "",
 *     "data": []
 * }
 */
@Getter
@Setter
@Builder
public class ApiResult {
    /**
     * SUCCESS 와 FAIL로 API 호출 상태를 구분
     */
    private ApiStatus status;

    /**
     * API 호출에 대한 메세지를 담아 반환
     */
    private String message;

    /**
     * API 호출에 대한 결과 데이터
     */
    private Object data;
}
