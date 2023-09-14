package com.portfolio.communityuser.dto;

import com.portfolio.communityuser.type.BoardType;
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
     * 게시글 작성 user ID
     */
    private String userId;

    /**
     * 게시글 작성 admin ID
     */
    private String adminId;

    /**
     * 카테고리 ID
     * <p>
     * 카테고리 ID는 "all"을 제외한 다른 값을 가져야 합니다.
     */
    @NotBlank(groups = {Free.class, Notice.class, Gallery.class},
            message = "{notBlank.boardDto.categoryId}")
    @Pattern(regexp = "^(?!all$).*$",
            groups = {Free.class, Notice.class, Gallery.class},
            message = "{pattern.boardDto.categoryId}")
    private String categoryId;

    /**
     * 제목
     */
    @NotBlank(groups = {Free.class, Notice.class, Gallery.class, Help.class},
            message = "{notBlank.boardDto.title}")
    @Size(max = 100,
            groups = {Free.class, Notice.class, Gallery.class, Help.class},
            message = "{size.boardDto.title}")
    private String title;

    /**
     * 내용
     */
    @NotBlank(groups = {Free.class, Notice.class, Gallery.class, Help.class},
            message = "{notBlank.boardDto.content}")
    @Size(max = 4000,
            groups = {Free.class, Notice.class, Gallery.class, Help.class},
            message = "{size.boardDto.content}")
    private String content;

    /**
     * 답변
     */
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
    private MultipartFile[] files = new MultipartFile[0];

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
     * 답변자
     */
    private String answerer;

    /**
     * 조회수
     */
    private String views;

    /**
     * 작성일
     */
    private String createdAt;

    /**
     * 답변일
     */
    private String answeredAt;

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
    private boolean hasAttached;
}
