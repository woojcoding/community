package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.CommentRequestDto;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.services.CommentService;
import com.portfolio.community.utils.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The type Comment controller.
 */
@Controller
@RequestMapping("/admin/boards/free/{boardId}")
@RequiredArgsConstructor
public class CommentController {

    /**
     * 댓글에 대한 로직을 처리하는 commentService 의존성 주입
     */
    private final CommentService commentService;

    /**
     * 댓글을 작성하는 메서드
     *
     * @param boardId              게시글 Id
     * @param boardSearchCondition 검색 조건
     * @param commentRequestDto    댓글 정보 Dto
     * @param model                the model
     * @return 작성된 글 상세보기 페이지
     */
    @PostMapping("/comment")
    public String postComment(
            @PathVariable("boardId") int boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @ModelAttribute("commentRequestDto")
            CommentRequestDto commentRequestDto,
            Model model
    ) {
        // 댓글을 post
        int adminId = AuthenticationUtil.getAdminId();

        commentRequestDto.setBoardId(boardId);
        commentRequestDto.setAdminId(adminId);

        commentService.postComment(commentRequestDto);

        // model에 값 지정
        model.addAttribute("boardSearch", boardSearchCondition);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/free/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword());

        return "redirect:" + builder.buildAndExpand(boardId).toUriString();
    }

    /**
     * 게시글에 작성된 댓글을 삭제하는 메서드
     *
     * @param boardId              게시글 Id
     * @param commentId            댓글 Id
     * @param boardSearchCondition 검색 조건
     * @return 작성된 글 상세보기 페이지
     */
    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(
            @PathVariable("boardId") int boardId,
            @PathVariable("commentId") int commentId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        commentService.deleteComment(commentId);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/free/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword());

        return "redirect:" + builder.buildAndExpand(boardId).toUriString();
    }
}
