package com.portfolio.community.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 파일 업로드와 다운로드를 위해 사용되는 DTO 클래스입니다.
 *
 * 이 DTO 클래스는 파일 관련 정보를 담고 있으며, 다음과 같은 필드를 가지고 있습니다:
 * - Integer fileId: 파일 ID
 * - String originalName: 원본 파일명
 * - String savedName: 저장된 파일명
 * - String thumbnailName: 저장된 썸네일명
 * - int boardId: 게시글 ID
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {

    private Integer fileId; // 파일 ID

    private String originalName; // 원본 파일명

    private String savedName; // 저장된 파일명

    private String thumbnailName; // 저장된 썸네일명

    private int boardId; // 게시글 Id
}
