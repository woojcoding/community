package com.portfolio.communityuser.controllers;

import com.portfolio.communityuser.dtos.BoardDto;
import com.portfolio.communityuser.repositories.BoardSearchCondition;
import com.portfolio.communityuser.services.HelpBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Help controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HelpBoardController {

    /**
     * 문의 게시글에 대한 로직을 처리하는 helpBoardService를 의존성 주입
     */
    private final HelpBoardService helpBoardService;

    /**
     * 다국어 처리를 지원하는 messageSource를 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 문의 게시글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return boardList 페이지 반환
     */
    @GetMapping("/boards/help")
    public ResponseEntity<ApiResult> getHelpBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        List<BoardDto> boardDtoList =
                helpBoardService.getHelpBoardList(boardSearchCondition);

        int totalBoardCount =
                helpBoardService.getTotalBoardCount(boardSearchCondition);

        Map<String, Object> data = new HashMap<>();
        data.put("boardList", boardDtoList);
        data.put("totalBoardCount", totalBoardCount);

        String message =
                messageSource.getMessage("get.boardList.success",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .data(data)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }
}
