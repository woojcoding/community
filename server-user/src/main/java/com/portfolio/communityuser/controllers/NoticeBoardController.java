package com.portfolio.communityuser.controllers;

import com.portfolio.communityuser.dtos.BoardDto;
import com.portfolio.communityuser.dtos.CategoryDto;
import com.portfolio.communityuser.enums.BoardType;
import com.portfolio.communityuser.repositories.BoardSearchCondition;
import com.portfolio.communityuser.services.CategoryService;
import com.portfolio.communityuser.services.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Notice controller.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NoticeBoardController {

    /**
     *  공지글에 대한 로직을 처리하는 noticeBoardService를 의존성 주입
     */
    private final NoticeBoardService noticeBoardService;

    /**
     * 카테고리에 대한 로직을 처리하는 categoryService를 의존성 주입
     */
    private final CategoryService categoryService;

    /**
     * 다국어 처리를 지원하는 messageSource를 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 공지글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return boardList 페이지 반환
     */
    @GetMapping("/boards/notice")
    public ResponseEntity<ApiResult> getNoticeBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        List<BoardDto> boardDtoList =
                noticeBoardService.getNoticeBoardList(boardSearchCondition);

        int totalBoardCount =
                noticeBoardService.getTotalBoardCount(boardSearchCondition);

        // 공지사항에서는 알림글을 가져와줌
        List<BoardDto> notificationDtoList =
                noticeBoardService.getNotificationList();

        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.NOTICE);

        Map<String, Object> data = new HashMap<>();
        data.put("notificationList", notificationDtoList);
        data.put("boardList", boardDtoList);
        data.put("totalBoardCount", totalBoardCount);
        data.put("categoryList", categoryList);

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .data(data)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 게시글 상세보기를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return writeView 반환
     */
    @GetMapping("/boards/notice/{boardId}")
    public ResponseEntity<ApiResult> getNoticeBoard(
            @PathVariable("boardId") Integer boardId
    ) {
        noticeBoardService.updateViews(boardId);

        // 게시글 정보를 조회
        BoardDto boardDto =
                noticeBoardService.getNoticeBoard(boardId);

        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.NOTICE);

        Map<String, Object> data = new HashMap<>();
        data.put("board", boardDto);
        data.put("categoryList", categoryList);

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .data(data)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }
}
