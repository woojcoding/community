package com.portfolio.community.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 게시글 목록 조회에 사용되는 DTO 클래스입니다.
 *
 * 이 DTO 클래스는 게시글 목록 조회 결과를 담고 있으며, 다음과 같은 필드들을 가지고 있습니다:
 * - List<BoardResponseDto> boardResponseDtoList: 게시글의 정보를 담은 리스트
 * - int totalBoardCount: 게시물 총 수
 */
@Getter
@Builder
public class BoardListDto {

    private List<BoardResponseDto> boardResponseDtoList; // 게시글의 정보를 담은 List

    private int totalBoardCount; // 게시물 총 수
}
