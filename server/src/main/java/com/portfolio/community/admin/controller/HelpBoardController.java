package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.BoardListDto;
import com.portfolio.community.dtos.BoardRequestDto;
import com.portfolio.community.enums.BoardType;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.services.HelpBoardService;
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


/**
 * The type Help controller.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class HelpBoardController {

    /**
     * 문의 게시글에 대한 로직을 처리하는 helpBoardService를 의존성 주입
     */
    private final HelpBoardService helpBoardService;

    /**
     * 문의 게시글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @param model                the model
     * @return boardList 페이지 반환
     */
    @GetMapping("/boards/help")
    public String getHelpBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            Model model
    ) {
        BoardListDto boardListDto =
                helpBoardService.getHelpBoardList(boardSearchCondition);

        model.addAttribute("boardListDto", boardListDto);
        model.addAttribute("boardSearch", boardSearchCondition);
        model.addAttribute("type", BoardType.HELP);

        return "admin/views/boardListView";
    }

    /**
     * 게시글 수정 폼을 가져오는 메서드
     *
     * @param boardSearchCondition 검색조건
     * @param boardId              게시글 Id
     * @param model                the model
     * @return writeView 반환
     */
    @GetMapping("/boards/help/{boardId}")
    public String getFreeWriteForm(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @PathVariable(value = "boardId") String boardId,
            Model model
    ) {
        // 게시글 정보를 조회
        BoardRequestDto boardRequestDto =
                helpBoardService.getHelpBoard(boardId);

        model.addAttribute("boardRequestDto", boardRequestDto);
        model.addAttribute("type", BoardType.HELP);

        return "admin/views/writeView";
    }

    /**
     * 문의글에 답변을 달아 업데이트 하는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @param boardId              the board id
     * @return 답변을 단 게시글 페이지로 redirect
     */
    @PostMapping("/boards/help/{boardId}")
    public String answerHelpBoard(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @ModelAttribute BoardRequestDto boardRequestDto,
            @PathVariable("boardId") int boardId
    ){
        int adminId = AuthenticationUtil.getAdminId();

        boardRequestDto.setAdminId(adminId);
        boardRequestDto.setBoardId(boardId);

        helpBoardService.answerHelpBoard(boardRequestDto);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/help/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword());

        return "redirect:" +
                builder.buildAndExpand(boardId).toUriString();
    }
}
