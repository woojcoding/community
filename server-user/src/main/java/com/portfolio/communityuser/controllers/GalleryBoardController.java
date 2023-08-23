package com.portfolio.communityuser.controllers;

import com.portfolio.communityuser.dtos.BoardDto;
import com.portfolio.communityuser.dtos.CategoryDto;
import com.portfolio.communityuser.dtos.FileDto;
import com.portfolio.communityuser.enums.BoardType;
import com.portfolio.communityuser.repositories.BoardSearchCondition;
import com.portfolio.communityuser.services.CategoryService;
import com.portfolio.communityuser.services.FileService;
import com.portfolio.communityuser.services.GalleryBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Gallery controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GalleryBoardController {

    /**
     * 갤러리 게시글에 대한 로직을 처리하는 galleryBoardService를 의존성 주입
     */
    private final GalleryBoardService galleryBoardService;

    /**
     * 카테고리에 대한 로직을 처리하는 categoryService를 의존성 주입
     */
    private final CategoryService categoryService;

    /**
     * 파일에 대한 로직을 처리하는 fileService를 의존성 주입
     */
    private final FileService fileService;

    /**
     * 다국어 처리를 지원하는 messageSource를 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 갤러리 게시글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return ResponseEntity<ApiResult>
     */
    @GetMapping("/boards/gallery")
    public ResponseEntity<ApiResult> getGalleryBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        List<BoardDto> boardDtoList =
                galleryBoardService.getGalleryBoardList(boardSearchCondition);

        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.GALLERY);

        Map<String, Object> data = new HashMap<>();
        data.put("boardList", boardDtoList);
        data.put("categoryList", categoryList);

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
     * 썸네일을 전달하는 메서드
     *
     * @param boardId 게시글 Id
     * @return
     */
    @GetMapping("/boards/gallery/{boardId}/thumbnail")
    public ResponseEntity<ApiResult> getThumbnail(
            @PathVariable("boardId") int boardId
    ) throws IOException {

        FileDto fileDto = fileService.getThumbnail(boardId);

        // 이미지 파일을 읽어서 Base64 인코딩
        Path imagePath = Paths.get(
                fileService.getFullPath(fileDto.getThumbnailName())
        );

        byte[] imageBytes = Files.readAllBytes(imagePath);

        String imageDataUrl = "data:image/jpeg;base64," +
                Base64.getEncoder().encodeToString(imageBytes);

        Map<String, Object> data = new HashMap<>();
        data.put("url", imageDataUrl);

        ApiResult apiResult = ApiResult.builder()
                .status(ApiStatus.SUCCESS)
                .data(data)
                .build();

        return ResponseEntity.ok().body(apiResult);
    }

    /**
     * 게시글 상세보기를 가져오는 메서드
     *
     * @param boardId 게시글 Id
     * @return ResponseEntity<ApiResult>
     */
    @GetMapping("/boards/gallery/{boardId}")
    public ResponseEntity<ApiResult> getGalleryBoard(
            @PathVariable("boardId") int boardId
    ) throws IOException {
        galleryBoardService.updateViews(boardId);

        // 게시글 정보를 조회
        BoardDto boardDto =
                galleryBoardService.getGalleryBoard(boardId);

        List<FileDto> fileDtoList = fileService.getFileList(boardId);

        // 이미지 파일을 읽어서 Base64 인코딩
        for (FileDto fileDto : fileDtoList) {
            Path imagePath = Paths.get(
                    fileService.getFullPath(fileDto.getSavedName())
            );

            byte[] imageBytes = Files.readAllBytes(imagePath);

            String imageUrl = "data:image/jpeg;base64," +
                    Base64.getEncoder().encodeToString(imageBytes);

            fileDto.setImageUrl(imageUrl);
        }

        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.GALLERY);

        Map<String, Object> data = new HashMap<>();
        data.put("board", boardDto);
        data.put("fileList", fileDtoList);
        data.put("categoryList", categoryList);

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
}
