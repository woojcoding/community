package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.BoardListDto;
import com.portfolio.community.dtos.CategoryDto;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.services.BoardService;
import com.portfolio.community.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 *  board에 대한  admin의 요청을 처리하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/boards")
public class BoardController {

    /**
     * board에 대한 로직을 처리하는 boardService를 의존성 주입
     */
    private final BoardService boardService;

    /**
     * 카테고리에 대한 로직을 처리하는 categoryService를 의존성 주
     */
    private final CategoryService categoryService;

    /**
     * 게시글 목록을 조회하는데 사용되는 메서드.
     *
     * @param boardSearchCondition 검색 조건
     * @param model                the model
     * @param type                 게시글의 타입
     * @return boardList 페이지 반환
     */
    @GetMapping("/{type}")
    public String getBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @PathVariable("type") String type,
            Model model
    ) {
        boardSearchCondition.setType(type);

        BoardListDto boardListDto =
                boardService.getBoardList(boardSearchCondition);

        // 공지사항에서는 알림글을 가져와줌
        if (type.equals("notice")) {
            BoardListDto notificationListDto =
                    boardService.getNotificationList();

            model.addAttribute("notificationListDto", notificationListDto);
        }

        List<CategoryDto> categoryList = categoryService.getCategoryList(type);

        model.addAttribute("boardListDto", boardListDto);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardSearch", boardSearchCondition);
        model.addAttribute("type", type);

        return "admin/views/boardListView";
    }
}
