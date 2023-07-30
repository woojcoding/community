package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.BoardListDto;
import com.portfolio.community.dtos.BoardRequestDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * board에 대한  admin의 요청을 처리하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BoardController {

    /**
     * board에 대한 로직을 처리하는 boardService를 의존성 주입
     */
    private final BoardService boardService;

    /**
     * 카테고리에 대한 로직을 처리하는 categoryService를 의존성 주입
     */
    private final CategoryService categoryService;

    /**
     * 게시글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @param type                 게시글의 타입
     * @param model                the model
     * @return boardList 페이지 반환
     */
    @GetMapping("/boards/{type}")
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

    /**
     * 게시글 작성/수정 폼을 가져오는 메서드
     *
     * @param boardSearchCondition 검색조건
     * @param type                 게시글 타입
     * @param boardId              게시글 Id - 수정인 경우에 필수
     * @param model                the model
     * @return writeView 반환
     */
    @GetMapping(value = {"/board/{type}/{boardId}", "/board/{type}"})
    public String getWriteForm(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @PathVariable("type") String type,
            @PathVariable(value = "boardId", required = false) String boardId,
            Model model
    ) {
        List<CategoryDto> categoryList = categoryService.getCategoryList(type);

        model.addAttribute("categoryList", categoryList);

        // boardId가 있다면 수정폼이므로 게시글 정보들을 model에 지정
        if (boardId != null) {
            BoardRequestDto boardRequestDto = boardService.getBoard(boardId);

            model.addAttribute("boardRequestDto", boardRequestDto);
        } else {
            model.addAttribute("boardRequestDto", new BoardRequestDto());
        }

        return "admin/views/writeView";
    }

    /**
     * 게시글을 post
     *
     * @param userId               작성자 Id
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @param type                 게시글 타입
     * @return 작성된 게시글 페이지로 redirect
     */
    @PostMapping("/board/{type}")
    public String postBoard(
            @RequestAttribute("userId") int userId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @ModelAttribute BoardRequestDto boardRequestDto,
            @PathVariable("type") String type
    ) {
        boardRequestDto.setType(type);
        boardRequestDto.setUserId(userId);

        boardService.postBoard(boardRequestDto);

        Integer savedBoardId = boardRequestDto.getBoardId();

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/board/{type}/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword());

        return "redirect:" +
                builder.buildAndExpand(type, savedBoardId).toUriString();
    }

    /**
     * 게시글을 update
     *
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @param type                 게시글 타입
     * @param boardId              the board id
     * @return 작성된 게시글 페이지로 redirect
     */
    @PostMapping("/board/{type}/{boardId}")
    public String updateBoard(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @ModelAttribute BoardRequestDto boardRequestDto,
            @PathVariable("type") String type,
            @PathVariable("boardId") int boardId
    ) {
        boardRequestDto.setBoardId(boardId);

        boardService.updateBoard(boardRequestDto);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/board/{type}/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword());

        return "redirect:" +
                builder.buildAndExpand(type, boardId).toUriString();
    }
}
