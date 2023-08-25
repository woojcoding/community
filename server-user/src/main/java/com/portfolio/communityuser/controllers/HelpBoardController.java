package com.portfolio.communityuser.controllers;

import com.portfolio.communityuser.dtos.BoardDto;
import com.portfolio.communityuser.dtos.Help;
import com.portfolio.communityuser.repositories.BoardSearchCondition;
import com.portfolio.communityuser.services.HelpBoardService;
import com.portfolio.communityuser.utils.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Help controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HelpBoardController {

    /**
     * 문의 게시글에 대한 로직을 처리하는 helpBoardService를 의존성 주입
     */
    private final HelpBoardService helpBoardService;

    /**
     * 다국어 처리를 지원하는 messageSource를 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 문의 게시글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return ResponseEntity<ApiResult>
     */
    @GetMapping("/boards/help")
    public ResponseEntity<ApiResult> getHelpBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        // 나의 문의 게시글만 보기를 선택한 경우
        if (boardSearchCondition.isDisplayMyPostsOnly()) {
            String userId = AuthenticationUtil.getAccountId();

            boardSearchCondition.setUserId(userId);
        }

        List<BoardDto> boardDtoList =
                helpBoardService.getHelpBoardList(boardSearchCondition);

        int totalBoardCount =
                helpBoardService.getTotalBoardCount(boardSearchCondition);

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
    @GetMapping("/boards/help/{boardId}")
    public ResponseEntity<ApiResult> getHelpBoard(
            @PathVariable("boardId") int boardId
    ) {
        // 게시글 정보를 조회
        BoardDto boardDto =
                helpBoardService.getHelpBoard(boardId);

        // 비밀글의 경우 본인 글만 볼 수 있도록 예외 처리
        if (boardDto.isSecretFlag()) {
            String boardUserId = boardDto.getUserId();

            String userId = AuthenticationUtil.getAccountId();

            if ((boardUserId == null || !boardUserId.equals(userId))) {
                throw new AccessDeniedException("access.denied");
            }
        }

        helpBoardService.updateViews(boardId);

        boardDto = helpBoardService.getHelpBoard(boardId);

        Map<String, Object> data = new HashMap<>();
        data.put("board", boardDto);

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
    @GetMapping("/boards/help/modify/{boardId}")
    public ResponseEntity<ApiResult> getHelpBoardForModify(
            @PathVariable("boardId") int boardId
    ) {
        // 게시글 정보를 조회
        BoardDto boardDto =
                helpBoardService.getHelpBoard(boardId);

        // 본인 글만 업데이트 페이지에 올 수 있도록 예외 처리
        String boardUserId = boardDto.getUserId();

        String userId = AuthenticationUtil.getAccountId();

        if ((boardUserId == null || !boardUserId.equals(userId))) {
            throw new AccessDeniedException("access.denied");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("board", boardDto);

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
     * 문의 게시글을 post
     *
     * @param boardDto      게시글 정보 Dto
     * @return ResponseEntity<ApiResult>
     */
    @PostMapping("/boards/help")
    public ResponseEntity<ApiResult> postFreeBoard(
            @Validated(Help.class) @RequestBody
            BoardDto boardDto
    ) {
        String userId = AuthenticationUtil.getAccountId();

        boardDto.setUserId(userId);

        // 게시글 저장
        helpBoardService.postHelpBoard(boardDto);

        int savedBoardId = boardDto.getBoardId();

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
     * 문의 게시글을 patch
     *
     * @param boardDto      게시글 정보 Dto
     * @return ResponseEntity<ApiResult>
     */
    @PatchMapping("/boards/help/{boardId}")
    public ResponseEntity<ApiResult> patchFreeBoard(
            @PathVariable("boardId") int boarId,
            @Validated(Help.class) @RequestBody
            BoardDto boardDto
    ) {
        // 본인 글만 업데이트 가능하도록 예외처리
        String boardUserId = boardDto.getUserId();

        String userId = AuthenticationUtil.getAccountId();

        if ((boardUserId == null || !boardUserId.equals(userId))) {
            throw new AccessDeniedException("access.denied");
        }

        // 게시글 업데이트
        helpBoardService.updateHelpBoard(boardDto);

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
     * 문의 게시글을 삭제하는 메서드
     *
     * @param boardId              게시글 Id
     * @return ResponseEntity<ApiResult>
     */
    @DeleteMapping("/boards/help/{boardId}")
    public ResponseEntity<ApiResult> deleteFreeBoard(
            @PathVariable("boardId") int boardId
    ) {
        // 본인 글만 삭제 가능하도록 예외처리
        BoardDto boardDto = helpBoardService.getHelpBoard(boardId);

        String boardUserId = boardDto.getUserId();

        String userId = AuthenticationUtil.getAccountId();

        if ((boardUserId == null || !boardUserId.equals(userId))) {
            throw new AccessDeniedException("access.denied");
        }

        // 게시글을 삭제
        helpBoardService.deleteHelpBoard(boardId);

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
}
