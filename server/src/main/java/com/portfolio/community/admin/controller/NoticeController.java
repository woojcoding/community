package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.BoardListDto;
import com.portfolio.community.dtos.BoardRequestDto;
import com.portfolio.community.dtos.CategoryDto;
import com.portfolio.community.enums.BoardType;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.services.CategoryService;
import com.portfolio.community.services.NoticeBoardService;
import com.portfolio.community.utils.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


/**
 * The type Notice controller.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class NoticeController {

    /**
     *  공지글에 대한 로직을 처리하는 noticeBoardService를 의존성 주입
     */
    private final NoticeBoardService noticeBoardService;

    /**
     * 카테고리에 대한 로직을 처리하는 categoryService를 의존성 주입
     */
    private final CategoryService categoryService;


    /**
     * 공지글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @param model                the model
     * @return boardList 페이지 반환
     */
    @GetMapping("/boards/notice")
    public String getNoticeBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            Model model
    ) {
        BoardListDto boardListDto =
                noticeBoardService.getNoticeBoardList(boardSearchCondition);

        // 공지사항에서는 알림글을 가져와줌
        BoardListDto notificationListDto = noticeBoardService.getNotificationList();

        model.addAttribute("notificationListDto", notificationListDto);


        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.NOTICE);

        model.addAttribute("boardListDto", boardListDto);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardSearch", boardSearchCondition);
        model.addAttribute("type", BoardType.NOTICE);

        return "admin/views/boardListView";
    }

    /**
     * 게시글 작성/수정 폼을 가져오는 메서드
     *
     * @param boardSearchCondition 검색조건
     * @param boardId              게시글 Id - 수정인 경우에 필수
     * @param model                the model
     * @return writeView 반환
     */
    @GetMapping(value = {"/board/notice/{boardId}", "/board/notice"})
    public String getNoticeWriteForm(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @PathVariable(value = "boardId", required = false) String boardId,
            Model model
    ) {
        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.NOTICE);

        model.addAttribute("categoryList", categoryList);

        // boardId가 있다면 수정폼이므로 게시글 정보들을 model에 지정
        if (boardId != null) {
            BoardRequestDto boardRequestDto =
                    noticeBoardService.getNoticeBoard(boardId);

            model.addAttribute("boardRequestDto", boardRequestDto);
        } else {
            model.addAttribute("boardRequestDto", new BoardRequestDto());
        }

        model.addAttribute("type", BoardType.NOTICE);

        return "admin/views/writeView";
    }

    /**
     * 게시글을 post
     *
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @return 작성된 게시글 페이지로 redirect
     */
    @PostMapping("/board/notice")
    public String postNoticeBoard(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @ModelAttribute BoardRequestDto boardRequestDto
    ) {
        int adminId = AuthenticationUtil.getAdminId();

        boardRequestDto.setAdminId(adminId);

        noticeBoardService.postNoticeBoard(boardRequestDto);

        Integer savedBoardId = boardRequestDto.getBoardId();

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/board/notice/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword());

        return "redirect:" +
                builder.buildAndExpand(savedBoardId).toUriString();
    }

    /**
     * 공지글을 update
     *
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @param boardId              the board id
     * @return 작성된 게시글 페이지로 redirect
     */
    @PostMapping("/board/notice/{boardId}")
    public String updateNoticeBoard(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @ModelAttribute BoardRequestDto boardRequestDto,
            @PathVariable("boardId") int boardId
    ) {
        boardRequestDto.setBoardId(boardId);

        noticeBoardService.updateNoticeBoard(boardRequestDto);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/board/notice/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword());

        return "redirect:" +
                builder.buildAndExpand(boardId).toUriString();
    }
}