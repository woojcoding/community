package com.portfolio.communityuser.controller;

import com.portfolio.communityuser.dto.BoardDto;
import com.portfolio.communityuser.dto.CommentDto;
import com.portfolio.communityuser.dto.FileDto;
import com.portfolio.communityuser.dto.Free;
import com.portfolio.communityuser.repository.BoardSearchCondition;
import com.portfolio.communityuser.service.CommentService;
import com.portfolio.communityuser.service.FileService;
import com.portfolio.communityuser.service.FreeBoardService;
import com.portfolio.communityuser.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
     * 파일에 대한 로직을 처리하는 fileService를 의존성 주입
     */
    private final FileService fileService;

    /**
     * 댓글에 대한 로직을 처리하는 commentService를 의존성 주입
     */
    private final CommentService commentService;

    /**
     * 다국어 처리를 지원하는 messageSource를 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 자유 게시글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return ResponseEntity<ApiResult>
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

        Map<String, Object> data = new HashMap<>();
        data.put("boardList", boardDtoList);
        data.put("totalBoardCount", totalBoardCount);

        String message =
                messageSource.getMessage("get.boardList.success",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .data(data)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 게시글 상세보기를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return ResponseEntity<ApiResult>
     */
    @GetMapping("/boards/free/{boardId}")
    public ResponseEntity<ApiResult> getFreeBoard(
            @PathVariable("boardId") int boardId
    ) {
        freeBoardService.updateViews(boardId);

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

        String message =
                messageSource.getMessage("get.board.success",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .data(data)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 게시글을 수정하기 위해 정보를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return ResponseEntity<ApiResult>
     * @throws AccessDeniedException 다른 사용자의 수정페이지에 접근할 경우 던지는 예외
     */
    @GetMapping("/boards/free/modify/{boardId}")
    public ResponseEntity<ApiResult> getFreeBoardForModify(
            @PathVariable("boardId") int boardId
    ) {
        // 게시글 정보를 조회
        BoardDto boardDto =
                freeBoardService.getFreeBoard(boardId);

        // 본인 글만 업데이트 페이지에 올 수 있도록 예외 처리
        String boardUserId = boardDto.getUserId();

        String userId = AuthenticationUtil.getAccountId();

        if ((boardUserId == null || !boardUserId.equals(userId))) {
            throw new AccessDeniedException("access.denied");
        }

        List<FileDto> fileDtoList = fileService.getFileList(boardId);

        Map<String, Object> data = new HashMap<>();
        data.put("board", boardDto);
        data.put("fileList", fileDtoList);

        String message =
                messageSource.getMessage("get.board.success",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .data(data)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 자유 게시글을 post
     *
     * @param boardDto      게시글 정보 Dto
     * @param bindingResult 검증오류 보관 객체
     * @return ResponseEntity<ApiResult>
     * @throws IOException the io exception
     */
    @PostMapping("/boards/free")
    public ResponseEntity<ApiResult> postFreeBoard(
            @Validated(Free.class) @ModelAttribute
            BoardDto boardDto,
            BindingResult bindingResult
    ) throws IOException {
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            return buildErrorResponse(bindingResult);
        }

        String userId = AuthenticationUtil.getAccountId();

        boardDto.setUserId(userId);

        // 게시글 저장
        freeBoardService.postFreeBoard(boardDto);

        int savedBoardId = boardDto.getBoardId();

        // 게시글에 첨부된 파일 업로드
        MultipartFile[] files = boardDto.getFiles();

        List<FileDto> fileDtoList = fileService.saveFiles(files, savedBoardId);

        fileService.uploadFiles(fileDtoList);

        String message =
                messageSource.getMessage("post.board.success",
                        null, LocaleContextHolder.getLocale());

        Map<String, Object> data = new HashMap<>();
        data.put("boardId", savedBoardId);

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .data(data)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 자유 게시글을 update
     *
     * @param boardId       게시글 Id
     * @param boardDto      게시글 정보 Dto
     * @param bindingResult 검증오류 보관 객체
     * @return ResponseEntity<ApiResult>
     * @throws IOException the io exception
     */
    @PatchMapping("/boards/free/{boardId}")
    public ResponseEntity<ApiResult> updateFreeBoard(
            @PathVariable("boardId") int boardId,
            @Validated(Free.class) @ModelAttribute
            BoardDto boardDto,
            BindingResult bindingResult
    ) throws IOException {
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            return buildErrorResponse(bindingResult);
        }

        // 본인 글만 업데이트 가능하도록 예외처리
        String boardUserId = boardDto.getUserId();

        String userId = AuthenticationUtil.getAccountId();

        if ((boardUserId == null || !boardUserId.equals(userId))) {
            throw new AccessDeniedException("access.denied");
        }

        // 게시글 업데이트
        freeBoardService.updateFreeBoard(boardDto);

        // 파일 삭제 적용
        fileService.deleteFiles(boardDto.getDeleteFileIdList());

        // 파일 업로드 적용
        MultipartFile[] files = boardDto.getFiles();

        List<FileDto> fileDtoList = fileService.saveFiles(files, boardId);

        fileService.uploadFiles(fileDtoList);

        String message =
                messageSource.getMessage("patch.board.success",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 자유 게시글을 삭제하는 메서드
     *
     * @param boardId              게시글 Id
     * @return ResponseEntity<ApiResult>
     */
    @DeleteMapping("/boards/free/{boardId}")
    public ResponseEntity<ApiResult> deleteFreeBoard(
            @PathVariable("boardId") int boardId
    ) {
        // 본인 글만 삭제 가능하도록 예외처리
        BoardDto boardDto = freeBoardService.getFreeBoard(boardId);

        String boardUserId = boardDto.getUserId();

        String userId = AuthenticationUtil.getAccountId();

        if ((boardUserId == null || !boardUserId.equals(userId))) {
            throw new AccessDeniedException("access.denied");
        }

        // 게시글을 삭제
        freeBoardService.deleteFreeBoard(boardId);

        String message =
                messageSource.getMessage("delete.board.success",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 댓글을 작성하는 메서드
     *
     * @param boardId    게시글 Id
     * @param commentDto 댓글정보
     * @return ResponseEntity<ApiResult>
     */
    @PostMapping("/boards/free/{boardId}/comment")
    public ResponseEntity<ApiResult> postComment(
            @PathVariable("boardId") int boardId,
            @Valid @RequestBody CommentDto commentDto
    ) {
        String userId = AuthenticationUtil.getAccountId();

        commentDto.setUserId(userId);
        commentDto.setBoardId(boardId);

        commentService.postComment(commentDto);

        String message =
                messageSource.getMessage("post.comment.success",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 댓글을 삭제하는 메서드
     *
     * @param commentId             댓글 Id
     * @return ResponseEntity<ApiResult>
     */
    @DeleteMapping("/boards/free/comments/{commentId}")
    public ResponseEntity<ApiResult> deleteComment(
            @PathVariable("commentId") int commentId
    ) {
        // 본인 글만 삭제 가능하도록 예외처리
        CommentDto comment = commentService.getComment(commentId);

        String commentUserId = comment.getUserId();

        String userId = AuthenticationUtil.getAccountId();

        if ((commentUserId == null || !commentUserId.equals(userId))) {
            throw new AccessDeniedException("access.denied");
        }

        // 게시글을 삭제
        commentService.deleteComment(commentId);

        String message =
                messageSource.getMessage("delete.comment.success",
                        null, LocaleContextHolder.getLocale());

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .message(message)
                .build();

        return ResponseEntity
                .ok()
                .body(apiResult);
    }

    /**
     * 유효성 검증 실패시 ResponseEntity<ApiResult>를 반환해주는 메서드
     *
     * @param bindingResult bindingResult
     * @return ResponseEntity<ApiResult>
     */
    private ResponseEntity<ApiResult> buildErrorResponse(BindingResult bindingResult) {
        StringBuilder errorMessageBuilder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessageBuilder.append(fieldError.getDefaultMessage());
            errorMessageBuilder.append("\n");
        }

        String combinedErrorMessage = errorMessageBuilder.toString();

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.FAIL)
                .message(combinedErrorMessage)
                .build();

        return ResponseEntity
                .badRequest()
                .body(apiResult);
    }
}
