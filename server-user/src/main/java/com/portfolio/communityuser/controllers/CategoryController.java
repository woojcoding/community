package com.portfolio.communityuser.controllers;

import com.portfolio.communityuser.dtos.CategoryDto;
import com.portfolio.communityuser.enums.BoardType;
import com.portfolio.communityuser.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Category controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {

    /**
     * 카테고리에 대한 로직을 처리하는 categoryService를 의존성 주입
     */
    private final CategoryService categoryService;

    /**
     * 다국어 처리를 지원하는 messageSource를 의존성 주입
     */
    private final MessageSource messageSource;

    /**
     * 카테고리를 반환해주는 메서드
     *
     * @param type 카테고리 타입 (gallery, free, notice)
     * @return ResponseEntity<ApiResult> 반환
     */
    @GetMapping("/categories/{type}")
    public ResponseEntity<ApiResult> getCategory(
            @PathVariable("type") String type
    ) {
        BoardType boardType = BoardType.FREE;

        switch (type) {
            case "gallery":
                boardType = BoardType.GALLERY;
                break;
            case "notice":
                boardType = BoardType.NOTICE;
                break;
        }

        List<CategoryDto> categoryList =
                categoryService.getCategoryList(boardType);

        Map<String, Object> data = new HashMap<>();
        data.put("categoryList", categoryList);

        String message =
                messageSource.getMessage("get.category.success",
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
