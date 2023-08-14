package com.portfolio.communityuser.enums;

import lombok.Getter;

/**
 * The enum Board type.
 */
@Getter
public enum BoardType {
    /**
     * 공지사항 board type.
     */
    NOTICE,
    /**
     * 자유게시판 board type.
     */
    FREE,
    /**
     * 갤러리 board type.
     */
    GALLERY,
    /**
     * 문의게시판 board type.
     */
    HELP;
}
