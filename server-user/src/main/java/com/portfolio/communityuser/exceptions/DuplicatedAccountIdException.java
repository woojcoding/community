package com.portfolio.communityuser.exceptions;

import lombok.Getter;

/**
 * 회원 아이디가 중복되었을 떄 반환해주는 예외입니다.
 */
@Getter
public class DuplicatedAccountIdException extends RuntimeException {
}
