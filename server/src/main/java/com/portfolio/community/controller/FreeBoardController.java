package com.portfolio.community.controller;

import com.portfolio.community.dto.BoardDto;
import com.portfolio.community.dto.CategoryDto;
import com.portfolio.community.dto.CommentDto;
import com.portfolio.community.dto.FileDto;
import com.portfolio.community.dto.Free;
import com.portfolio.community.type.BoardType;
import com.portfolio.community.type.FormType;
import com.portfolio.community.repository.BoardSearchCondition;
import com.portfolio.community.service.CategoryService;
import com.portfolio.community.service.CommentService;
import com.portfolio.community.service.FileService;
import com.portfolio.community.service.FreeBoardService;
import com.portfolio.community.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Free controller.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class FreeBoardController {

    /**
     *  자유 게시글에 대한 로직을 처리하는 freeBoardService를 의존성 주입
     */
    private final FreeBoardService freeBoardService;

    /**
     * 카테고리에 대한 로직을 처리하는 categoryService를 의존성 주입
     */
    private final CategoryService categoryService;

    /**
     * 파일에 대한 로직을 처리하는 fileService를 의존성 주입
     */
    private final FileService fileService;

    /**
     * 댓글에 대한 로직을 처리하는 commentService를 의존성 주입
     */
    private final CommentService commentService;

    /**
     * 자유 게시글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @param model                the model
     * @return boardList 페이지 반환
     */
    @GetMapping("/boards/free")
    public String getFreeBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            Model model
    ) {
        List<BoardDto> boardDtoList =
                freeBoardService.getFreeBoardList(boardSearchCondition);

        int totalBoardCount =
                freeBoardService.getTotalBoardCount(boardSearchCondition);

        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.FREE);

        model.addAttribute("boardDtoList", boardDtoList);
        model.addAttribute("totalBoardCount", totalBoardCount);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardSearch", boardSearchCondition);
        model.addAttribute("type", BoardType.FREE.toString());

        return "admin/views/boardListView";
    }

    /**
     * 게시글 작성/수정 폼을 가져오는 메서드
     *
     * @param boardId              게시글 Id - 수정인 경우에 필수
     * @param boardSearchCondition 검색조건
     * @param model                the model
     * @return writeView 반환
     */
    @GetMapping(value = {"/boards/free/{boardId}", "/board/free"})
    public String getFreeWriteForm(
            @PathVariable(value = "boardId", required = false) Integer boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            Model model
    ) {
        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.FREE);

        model.addAttribute("categoryList", categoryList);

        // boardId가 있다면 수정폼이므로 게시글 정보들을 model에 지정
        if (boardId != null) {
            // 게시글 정보를 조회
            BoardDto boardDto =
                    freeBoardService.getFreeBoard(boardId);

            List<FileDto> fileDtoList = fileService.getFileList(boardId);

            List<CommentDto> commentList =
                    commentService.getCommentList(boardId);

            model.addAttribute("boardDto", boardDto);
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("commentList", commentList);
            model.addAttribute("commentDto", new CommentDto());
            model.addAttribute("formType", FormType.MODIFY);
        } else {
            List<FileDto> fileDtoList = new ArrayList<>();

            model.addAttribute("boardDto", new BoardDto());
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("formType", FormType.POST);
        }

        model.addAttribute("type", BoardType.FREE.toString());

        return "admin/views/writeView";
    }

    /**
     * 자유 게시글을 post
     *
     * @param boardSearchCondition 검색 조건
     * @param boardDto      게시글 정보 Dto
     * @param bindingResult        검증오류 보관 객체
     * @param model                the model
     * @return 작성된 게시글 페이지로 redirect
     * @throws IOException the io exception
     */
    @PostMapping("/board/free")
    public String postFreeBoard(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @Validated(Free.class) @ModelAttribute
            BoardDto boardDto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            List<FileDto> fileDtoList = new ArrayList<>();

            List<CategoryDto> categoryList =
                    categoryService.getCategoryList(BoardType.FREE);

            model.addAttribute("categoryList", categoryList);
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("type", BoardType.FREE.toString());
            model.addAttribute("formType", FormType.POST);

            return "admin/views/writeView";
        }

        String adminId = AuthenticationUtil.getAdminId();

        boardDto.setAdminId(adminId);

        // 게시글 저장
        freeBoardService.postFreeBoard(boardDto);

        int savedBoardId = boardDto.getBoardId();

        // 게시글에 첨부된 파일 업로드
        MultipartFile[] files = boardDto.getFiles();

        List<FileDto> fileDtoList = fileService.saveFiles(files, savedBoardId);

        fileService.uploadFiles(fileDtoList);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/free/{boardId}")
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
     * 자유 게시글을 update
     *
     * @param boardId              게시글 Id
     * @param boardSearchCondition 검색 조건
     * @param boardDto      게시글 정보 Dto
     * @param bindingResult        검증오류 보관 객체
     * @param model                the model
     * @return 작성된 게시글 페이지로 redirect
     * @throws IOException the io exception
     */
    @PostMapping("/boards/free/{boardId}")
    public String updateFreeBoard(
            @PathVariable("boardId") Integer boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @Validated(Free.class) @ModelAttribute
            BoardDto boardDto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            List<CategoryDto> categoryList =
                    categoryService.getCategoryList(BoardType.FREE);

            List<FileDto> fileDtoList = fileService.getFileList(boardId);

            List<CommentDto> commentList =
                    commentService.getCommentList(boardId);

            model.addAttribute("categoryList", categoryList);
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("commentList", commentList);
            model.addAttribute("commentDto", new CommentDto());
            model.addAttribute("formType", FormType.MODIFY);
            model.addAttribute("type", BoardType.FREE.toString());

            return "admin/views/writeView";
        }

        boardDto.setBoardId(boardId);

        // 게시글 업데이트
        freeBoardService.updateFreeBoard(boardDto);

        // 파일 삭제 적용
        fileService.deleteFiles(boardDto.getDeleteFileIdList());

        // 파일 업로드 적용
        MultipartFile[] files = boardDto.getFiles();

        List<FileDto> fileDtoList = fileService.saveFiles(files, boardId);

        fileService.uploadFiles(fileDtoList);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/free/{boardId}")
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
     * 자유 게시글을 삭제하는 메서드
     *
     * @param boardId              게시글 Id
     * @param boardSearchCondition 검색 조건

     * @return 삭제 후 게시글 목록으로 이동
     */
    @GetMapping("/board/free/delete/{boardId}")
    public String deleteFreeBoard(
            @PathVariable("boardId") int boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        // 게시글을 삭제
        freeBoardService.deleteFreeBoard(boardId);

        // 검색 조건을 유지시켜 게시글 리스트 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/free/")
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

    /**
     * 댓글을 작성하는 메서드
     *
     * @param boardId              게시글 Id
     * @param boardSearchCondition 검색 조건
     * @param commentDto    댓글 정보 Dto
     * @param bindingResult        검증오류 보관 객체
     * @param model                the model
     * @return 작성된 글 상세보기 페이지
     */
    @PostMapping("/boards/free/{boardId}/comment")
    public String postComment(
            @PathVariable("boardId") int boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @Validated @ModelAttribute("commentDto")
            CommentDto commentDto,
            BindingResult bindingResult,
            Model model
    ) {
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            BoardDto boardDto =
                    freeBoardService.getFreeBoard(boardId);

            List<FileDto> fileDtoList = fileService.getFileList(boardId);

            List<CommentDto> commentList =
                    commentService.getCommentList(boardId);

            model.addAttribute("boardDto", boardDto);
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("commentList", commentList);
            model.addAttribute("formType", FormType.MODIFY);
            model.addAttribute("type", BoardType.FREE.toString());

            return "admin/views/writeView";
        }

        // 댓글을 post
        String adminId = AuthenticationUtil.getAdminId();

        commentDto.setBoardId(boardId);
        commentDto.setAdminId(adminId);

        commentService.postComment(commentDto);

        // model에 값 지정
        model.addAttribute("boardSearch", boardSearchCondition);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/free/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("pageSize", boardSearchCondition.getPageSize())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword())
                .queryParam("sortBy", boardSearchCondition.getSortBy())
                .queryParam("sort", boardSearchCondition.getSort());

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
    @DeleteMapping("/boards/free/{boardId}/comments/{commentId}")
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
                .queryParam("pageSize", boardSearchCondition.getPageSize())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword())
                .queryParam("sortBy", boardSearchCondition.getSortBy())
                .queryParam("sort", boardSearchCondition.getSort());

        return "redirect:" + builder.buildAndExpand(boardId).toUriString();
    }
}
