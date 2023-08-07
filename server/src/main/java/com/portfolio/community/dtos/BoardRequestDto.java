package com.portfolio.community.dtos;

import com.portfolio.community.enums.BoardType;
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
 * 게시글 등록에 사용되는 DTO 클래스입니다.
 * <p>
 * 이 DTO 클래스는 게시글 등록에 필요한 정보를 담고 있으며, 다음과 같은 필드들을 가지고 있습니다:
 * - Integer boardId: 반환되는 게시글 ID
 * - int adminId: 어드민 아이디
 * - String categoryId: 카테고리 ID
 * - String title: 제목
 * - String content: 내용
 * - BoardType type: 게시글의 타입
 * - boolean notificaionFlag: 알림글 여부
 * - boolean secretFlag: 비밀글 여부
 * - String status: 상태
 * - String answer: 답변
 * - MultipartFile[] files: 파일들
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardRequestDto {

    private Integer boardId; // 반환되는 게시글 Id

    private int adminId; // 게시글 작성 admin Id

    @Pattern(regexp = "^(?!all$).*$",
            groups = {Free.class, Notice.class, Gallery.class})
    private String categoryId; // 카테고리 Id

    @NotBlank(groups = {Free.class, Notice.class, Gallery.class, Help.class})
    @Size(max = 100,
            groups = {Free.class, Notice.class, Gallery.class, Help.class})
    private String title; // 제목

    @NotBlank(groups = {Free.class, Notice.class, Gallery.class, Help.class})
    @Size(max = 4000,
            groups = {Free.class, Notice.class, Gallery.class, Help.class})
    private String content; // 내용

    @NotBlank(groups = Help.class)
    @Size(max = 4000, groups = Help.class)
    private String answer; // 답변

    private BoardType type; // 타입

    private boolean notificationFlag; // 알림글 여부

    private boolean secretFlag; // 비밀글 여부

    private MultipartFile[] files; // 파일들

    private List<Integer> deleteFileIdList = new ArrayList<>(); // 삭제할 파일 Id list
}
