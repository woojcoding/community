package com.portfolio.community.controller;

import com.portfolio.community.dto.BoardDto;
import com.portfolio.community.dto.CategoryDto;
import com.portfolio.community.dto.Notice;
import com.portfolio.community.type.BoardType;
import com.portfolio.community.type.FormType;
import com.portfolio.community.repository.BoardSearchCondition;
import com.portfolio.community.service.CategoryService;
import com.portfolio.community.service.NoticeBoardService;
import com.portfolio.community.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
        List<BoardDto> boardDtoList =
                noticeBoardService.getNoticeBoardList(boardSearchCondition);

        int totalBoardCount =
                noticeBoardService.getTotalBoardCount(boardSearchCondition);

        // 공지사항에서는 알림글을 가져와줌
        List<BoardDto> notificationDtoList =
                noticeBoardService.getNotificationList();

        model.addAttribute("notificationDtoList", notificationDtoList);


        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.NOTICE);

        model.addAttribute("boardDtoList", boardDtoList);
        model.addAttribute("totalBoardCount", totalBoardCount);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardSearch", boardSearchCondition);
        model.addAttribute("type", BoardType.NOTICE.toString());

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
    @GetMapping(value = {"/boards/notice/{boardId}", "/board/notice"})
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
            BoardDto boardDto =
                    noticeBoardService.getNoticeBoard(boardId);

            model.addAttribute("boardDto", boardDto);
            model.addAttribute("formType", FormType.MODIFY);
        } else {
            model.addAttribute("boardDto", new BoardDto());
            model.addAttribute("formType", FormType.POST);
        }

        model.addAttribute("type", BoardType.NOTICE.toString());

        return "admin/views/writeView";
    }

    /**
     * 게시글을 post
     *
     * @param boardSearchCondition 검색 조건
     * @param boardDto      게시글 정보 Dto
     * @param bindingResult        검증오류 보관 객체
     * @param model                the model
     * @return 작성된 게시글 페이지로 redirect
     */
    @PostMapping("/board/notice")
    public String postNoticeBoard(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @Validated(Notice.class) @ModelAttribute
            BoardDto boardDto,
            BindingResult bindingResult,
            Model model
    ) {
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            List<CategoryDto> categoryList =
                    categoryService.getCategoryList(BoardType.NOTICE);

            model.addAttribute("categoryList", categoryList);
            model.addAttribute("type", BoardType.NOTICE.toString());
            model.addAttribute("formType", FormType.POST);

            return "admin/views/writeView";
        }

        String adminId = AuthenticationUtil.getAdminId();

        boardDto.setAdminId(adminId);

        noticeBoardService.postNoticeBoard(boardDto);

        Integer savedBoardId = boardDto.getBoardId();

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/notice/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("pageSize", boardSearchCondition.getPageSize())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword())
                .queryParam("sortBy", boardSearchCondition.getSortBy())
                .queryParam("sort", boardSearchCondition.getSort());

        return "redirect:" +
                builder.buildAndExpand(savedBoardId).toUriString();
    }

    /**
     * 공지글을 update
     *
     * @param boardId              the board id
     * @param boardSearchCondition 검색 조건
     * @param boardDto      게시글 정보 Dto
     * @param bindingResult        검증오류 보관 객체
     * @param model                the model
     * @return 작성된 게시글 페이지로 redirect
     */
    @PostMapping("/boards/notice/{boardId}")
    public String updateNoticeBoard(
            @PathVariable("boardId") int boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @Validated(Notice.class) @ModelAttribute
            BoardDto boardDto,
            BindingResult bindingResult,
            Model model
    ) {
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            List<CategoryDto> categoryList =
                    categoryService.getCategoryList(BoardType.NOTICE);

            model.addAttribute("categoryList", categoryList);
            model.addAttribute("type", BoardType.NOTICE.toString());
            model.addAttribute("formType", FormType.MODIFY);

            return "admin/views/writeView";
        }

        boardDto.setBoardId(boardId);

        noticeBoardService.updateNoticeBoard(boardDto);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/notice/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("pageSize", boardSearchCondition.getPageSize())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword())
                .queryParam("sortBy", boardSearchCondition.getSortBy())
                .queryParam("sort", boardSearchCondition.getSort());

        return "redirect:" +
                builder.buildAndExpand(boardId).toUriString();
    }

    /**
     * 공지 게시글을 삭제하는 메서드
     *
     * @param boardId              게시글 Id
     * @param boardSearchCondition 검색 조건

     * @return 삭제 후 게시글 목록으로 이동
     */
    @GetMapping("/board/notice/delete/{boardId}")
    public String deleteNoticeBoard(
            @PathVariable("boardId") int boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        // 게시글을 삭제
        noticeBoardService.deleteNoticeBoard(boardId);

        // 검색 조건을 유지시켜 게시글 리스트 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/notice/")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("pageSize", boardSearchCondition.getPageSize())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword())
                .queryParam("sortBy", boardSearchCondition.getSortBy())
                .queryParam("sort", boardSearchCondition.getSort());

        return "redirect:" + builder.build().toUriString();
    }
}
