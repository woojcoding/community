package com.portfolio.community.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Board type.
 */
@Getter
@AllArgsConstructor
public enum BoardType {

    /**
     * 공지사항 board type.
     */
    NOTICE("n"),
    /**
     * 자유게시판 board type.
     */
    FREE("f"),
    /**
     * 갤러리 board type.
     */
    GALLERY("g"),
    /**
     * 문의게시판 board type.
     */
    HELP("h");

    private String code;
}
