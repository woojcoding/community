package com.portfolio.community.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormType {
    POST("등록"),
    MODIFY("수정"),
    ANSWER("답변");

    private String code;
}
