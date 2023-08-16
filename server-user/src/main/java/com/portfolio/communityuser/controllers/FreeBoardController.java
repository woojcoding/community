package com.portfolio.communityuser.controllers;

import com.portfolio.communityuser.dtos.BoardDto;
import com.portfolio.communityuser.dtos.CategoryDto;
import com.portfolio.communityuser.dtos.CommentDto;
import com.portfolio.communityuser.dtos.FileDto;
import com.portfolio.communityuser.enums.BoardType;
import com.portfolio.communityuser.repositories.BoardSearchCondition;
import com.portfolio.communityuser.services.CategoryService;
import com.portfolio.communityuser.services.CommentService;
import com.portfolio.communityuser.services.FileService;
import com.portfolio.communityuser.services.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Free controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FreeBoardController {

    /**
     * 자유 게시글에 대한 로직을 처리하는 freeBoardService를 의존성 주입
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
     * @return boardList 페이지 반환
     */
    @GetMapping("/boards/free")
    public ResponseEntity<ApiResult> getFreeBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        List<BoardDto> boardDtoList =
                freeBoardService.getFreeBoardList(boardSearchCondition);

        int totalBoardCount =
                freeBoardService.getTotalBoardCount(boardSearchCondition);

        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.FREE);

        Map<String, Object> data = new HashMap<>();
        data.put("boardList", boardDtoList);
        data.put("totalBoardCount", totalBoardCount);
        data.put("categoryList", categoryList);

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .data(data)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 게시글 상세보기를 가져오는 메서드
     *
     * @param boardId              게시글 Id - 수정인 경우에 필수
     * @return writeView 반환
     */
    @GetMapping("/boards/free/{boardId}")
    public ResponseEntity<ApiResult> getFreeBoard(
            @PathVariable("boardId") Integer boardId
    ) {
        // 게시글 정보를 조회
        BoardDto boardDto =
                freeBoardService.getFreeBoard(boardId);

        List<FileDto> fileDtoList = fileService.getFileList(boardId);

        List<CommentDto> commentList =
                commentService.getCommentList(boardId);

        Map<String, Object> data = new HashMap<>();
        data.put("board", boardDto);
        data.put("fileList", fileDtoList);
        data.put("commentList", commentList);

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .data(data)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }
}
