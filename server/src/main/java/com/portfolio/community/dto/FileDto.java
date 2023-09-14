package com.portfolio.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 파일 업로드와 다운로드를 위해 사용되는 DTO 클래스입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {

    /**
     * 파일 ID
     */
    private Integer fileId;

    /**
     * 원본 파일명
     */
    private String originalName;

    /**
     * 저장된 파일명
     */
    private String savedName;

    /**
     * 저장된 썸네일명
     */
    private String thumbnailName;

    /**
     * 게시글 ID
     */
    private int boardId;
}
