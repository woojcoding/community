package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.BoardListDto;
import com.portfolio.community.dtos.BoardRequestDto;
import com.portfolio.community.dtos.CategoryDto;
import com.portfolio.community.dtos.CommentRequestDto;
import com.portfolio.community.dtos.FileDto;
import com.portfolio.community.enums.BoardType;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.services.CategoryService;
import com.portfolio.community.services.CommentService;
import com.portfolio.community.services.FileService;
import com.portfolio.community.services.FreeBoardService;
import com.portfolio.community.utils.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        BoardListDto boardListDto =
                freeBoardService.getFreeBoardList(boardSearchCondition);

        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.FREE);

        model.addAttribute("boardListDto", boardListDto);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardSearch", boardSearchCondition);
        model.addAttribute("type", BoardType.FREE);

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
    @GetMapping(value = {"/boards/free/{boardId}", "/board/free"})
    public String getFreeWriteForm(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @PathVariable(value = "boardId", required = false) String boardId,
            Model model
    ) {
        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.FREE);

        model.addAttribute("categoryList", categoryList);

        // boardId가 있다면 수정폼이므로 게시글 정보들을 model에 지정
        if (boardId != null) {
            // 게시글 정보를 조회
            BoardRequestDto boardRequestDto =
                    freeBoardService.getFreeBoard(boardId);

            List<FileDto> fileDtoList = fileService.getFileList(boardId);

            List<CommentRequestDto> commentList =
                    commentService.getCommentList(boardId);

            model.addAttribute("boardRequestDto", boardRequestDto);
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("commentList", commentList);
            model.addAttribute("commentRequestDto", new CommentRequestDto());
            model.addAttribute("mode", "modify");
        } else {
            List<FileDto> fileDtoList = new ArrayList<>();

            model.addAttribute("boardRequestDto", new BoardRequestDto());
            model.addAttribute("fileDtoList", fileDtoList);
        }

        model.addAttribute("type", BoardType.FREE);

        return "admin/views/writeView";
    }

    /**
     * 자유 게시글을 post
     *
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @return 작성된 게시글 페이지로 redirect
     */
    @PostMapping("/board/free")
    public String postFreeBoard(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @ModelAttribute BoardRequestDto boardRequestDto
    ) throws IOException {
        int adminId = AuthenticationUtil.getAdminId();

        boardRequestDto.setAdminId(adminId);

        // 게시글 저장
        freeBoardService.postFreeBoard(boardRequestDto);

        int savedBoardId = boardRequestDto.getBoardId();

        // 게시글에 첨부된 파일 업로드
        MultipartFile[] files = boardRequestDto.getFiles();

        List<FileDto> fileDtoList = fileService.saveFiles(files, savedBoardId);

        fileService.uploadFiles(fileDtoList);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/free/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword());

        return "redirect:" +
                builder.buildAndExpand(savedBoardId).toUriString();
    }

    /**
     * 자유 게시글을 update
     *
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @param boardId              the board id
     * @return 작성된 게시글 페이지로 redirect
     */
    @PostMapping("/boards/free/{boardId}")
    public String updateFreeBoard(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @ModelAttribute BoardRequestDto boardRequestDto,
            @PathVariable("boardId") int boardId
    ) throws IOException {
        boardRequestDto.setBoardId(boardId);

        // 게시글 업데이트
        freeBoardService.updateFreeBoard(boardRequestDto);

        // 파일 삭제 적용
        fileService.deleteFiles(boardRequestDto.getDeleteFileIdList());

        // 파일 업로드 적용
        MultipartFile[] files = boardRequestDto.getFiles();

        List<FileDto> fileDtoList = fileService.saveFiles(files, boardId);

        fileService.uploadFiles(fileDtoList);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/free/{boardId}")
                .queryParam("pageNum", boardSearchCondition.getPageNum())
                .queryParam("startDate", boardSearchCondition.getStartDate())
                .queryParam("endDate", boardSearchCondition.getEndDate())
                .queryParam("category", boardSearchCondition.getCategory())
                .queryParam("keyword", boardSearchCondition.getKeyword());

        return "redirect:" +
                builder.buildAndExpand(boardId).toUriString();
    }
}
