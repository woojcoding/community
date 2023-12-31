package com.portfolio.community.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * API 호출의 결과를 담고 있는 enum입니다.
 */
@Getter
@RequiredArgsConstructor
public enum ApiStatus {
    /**
     * Success api status.
     */
    SUCCESS("success"),

    /**
     * Fail api status.
     */
    FAIL("fail");

    /**
     * 메세지
     */
    private final String message;
}
