package com.portfolio.community.dto;

import com.portfolio.community.type.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 정보 DTO 클래스입니다.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

    /**
     * 반환되는 게시글 ID
     */
    private Integer boardId;

    /**
     * 게시글 작성 admin ID
     */
    private String adminId;

    /**
     * 카테고리 ID
     * <p>
     * 카테고리 ID는 "all"을 제외한 다른 값을 가져야 합니다.
     */
    @Pattern(regexp = "^(?!all$).*$",
            groups = {Free.class, Notice.class, Gallery.class})
    private String categoryId;

    /**
     * 제목
     */
    @NotBlank(groups = {Free.class, Notice.class, Gallery.class, Help.class})
    @Size(max = 100,
            groups = {Free.class, Notice.class, Gallery.class, Help.class})
    private String title;

    /**
     * 내용
     */
    @NotBlank(groups = {Free.class, Notice.class, Gallery.class, Help.class})
    @Size(max = 4000,
            groups = {Free.class, Notice.class, Gallery.class, Help.class})
    private String content;

    /**
     * 답변
     */
    @NotBlank(groups = Help.class)
    @Size(max = 4000, groups = Help.class)
    private String answer;

    /**
     * 게시글의 타입
     */
    private BoardType type;

    /**
     * 알림글 여부
     */
    private boolean notificationFlag;

    /**
     * 비밀글 여부
     */
    private boolean secretFlag;

    /**
     * 파일들
     */
    private MultipartFile[] files;

    /**
     * 삭제할 파일 ID 목록
     */
    private List<Integer> deleteFileIdList = new ArrayList<>();

    /**
     * 카테고리명
     */
    private String categoryName;

    /**
     * 작성자
     */
    private String writer;

    /**
     * 조회수
     */
    private String views;

    /**
     * 작성일
     */
    private String createdAt;

    /**
     * 썸네일 이름
     */
    private String thumbnailName;

    /**
     * 이미지 개수
     */
    private int imageCount;

    /**
     * 댓글 개수
     */
    private int commentCount;

    /**
     * 파일 첨부 여부
     */
    private boolean isAttached;
}
