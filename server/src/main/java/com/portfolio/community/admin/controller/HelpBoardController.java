package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.BoardListDto;
import com.portfolio.community.dtos.BoardRequestDto;
import com.portfolio.community.dtos.Help;
import com.portfolio.community.enums.BoardType;
import com.portfolio.community.enums.FormType;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.services.HelpBoardService;
import com.portfolio.community.utils.AuthenticationUtil;
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
        model.addAttribute("type", BoardType.HELP.toString());

        return "admin/views/boardListView";
    }

    /**
     * 게시글 수정 폼을 가져오는 메서드
     *
     * @param boardId              게시글 Id
     * @param boardSearchCondition 검색조건
     * @param model                the model
     * @return writeView 반환
     */
    @GetMapping("/boards/help/{boardId}")
    public String getHelpWriteForm(
            @PathVariable(value = "boardId") String boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            Model model
    ) {
        // 게시글 정보를 조회
        BoardRequestDto boardRequestDto =
                helpBoardService.getHelpBoard(boardId);

        model.addAttribute("boardRequestDto", boardRequestDto);
        model.addAttribute("type", BoardType.HELP.toString());
        model.addAttribute("formType", FormType.ANSWER);

        return "admin/views/writeView";
    }

    /**
     * 문의글에 답변을 달아 업데이트 하는 메서드
     *
     * @param boardId              게시글 Id
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @param bindingResult        검증오류 보관 객체
     * @param model                the model
     * @return 답변을 단 게시글 페이지로 redirect
     */
    @PostMapping("/boards/help/{boardId}")
    public String answerHelpBoard(
            @PathVariable("boardId") int boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @Validated(Help.class) @ModelAttribute
            BoardRequestDto boardRequestDto,
            BindingResult bindingResult,
            Model model
    ){
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            model.addAttribute("type", BoardType.HELP.toString());
            model.addAttribute("formType", FormType.ANSWER);

            return "admin/views/writeView";
        }
        int adminId = AuthenticationUtil.getAdminId();

        boardRequestDto.setAdminId(adminId);
        boardRequestDto.setBoardId(boardId);

        helpBoardService.answerHelpBoard(boardRequestDto);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/help/{boardId}")
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
     * 게시글을 삭제하는 메서드
     *
     * @param boardId              게시글 Id
     * @param boardSearchCondition 검색 조건

     * @return 삭제 후 게시글 목록으로 이동
     */
    @GetMapping("/board/help/delete/{boardId}")
    public String deleteHelpBoard(
            @PathVariable("boardId") int boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        // 게시글을 삭제
        helpBoardService.deleteHelpBoard(boardId);

        // 검색 조건을 유지시켜 게시글 리스트 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/help/")
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
