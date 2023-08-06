package com.portfolio.community.admin.controller;

import com.portfolio.community.dtos.BoardListDto;
import com.portfolio.community.dtos.BoardRequestDto;
import com.portfolio.community.dtos.CategoryDto;
import com.portfolio.community.dtos.FileDto;
import com.portfolio.community.dtos.Gallery;
import com.portfolio.community.enums.BoardType;
import com.portfolio.community.enums.FormType;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.services.CategoryService;
import com.portfolio.community.services.FileService;
import com.portfolio.community.services.GalleryBoardService;
import com.portfolio.community.utils.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Gallery controller.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class GalleryBoardController {

    /**
     *  갤러리 게시글에 대한 로직을 처리하는 galleryBoardService를 의존성 주입
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
     * 갤러리 게시글 목록을 조회하는데 사용되는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @param model                the model
     * @return boardList 페이지 반환
     */
    @GetMapping("/boards/gallery")
    public String getGalleryBoardList(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            Model model
    ) {
        BoardListDto boardListDto =
                galleryBoardService.getGalleryBoardList(boardSearchCondition);

        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.GALLERY);

        model.addAttribute("boardListDto", boardListDto);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardSearch", boardSearchCondition);
        model.addAttribute("type", BoardType.GALLERY);

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
    @GetMapping(value = {"/boards/gallery/{boardId}", "/board/gallery"})
    public String getGalleryWriteForm(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @PathVariable(value = "boardId", required = false) Integer boardId,
            Model model
    ) {
        List<CategoryDto> categoryList =
                categoryService.getCategoryList(BoardType.GALLERY);

        model.addAttribute("categoryList", categoryList);

        // boardId가 있다면 수정폼이므로 게시글 정보들을 model에 지정
        if (boardId != null) {
            // 게시글 정보를 조회
            BoardRequestDto boardRequestDto =
                    galleryBoardService.getGalleryBoard(boardId);

            List<FileDto> fileDtoList = fileService.getFileList(boardId);

            model.addAttribute("boardRequestDto", boardRequestDto);
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("formType", FormType.MODIFY);
        } else {
            List<FileDto> fileDtoList = new ArrayList<>();

            model.addAttribute("boardRequestDto", new BoardRequestDto());
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("formType", FormType.POST);
        }

        model.addAttribute("type", BoardType.GALLERY);

        return "admin/views/writeView";
    }

    /**
     * 갤러리 게시글을 post
     *
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @param bindingResult        검증오류 보관 객체
     * @param model                the model
     * @return 작성된 게시글 페이지로 redirect
     * @throws IOException the io exception
     */
    @PostMapping("/board/gallery")
    public String postGalleryBoard(
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @Validated(Gallery.class) @ModelAttribute
            BoardRequestDto boardRequestDto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            List<FileDto> fileDtoList = new ArrayList<>();

            List<CategoryDto> categoryList =
                    categoryService.getCategoryList(BoardType.GALLERY);

            model.addAttribute("categoryList", categoryList);
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("type", BoardType.GALLERY);
            model.addAttribute("formType", FormType.POST);

            return "admin/views/writeView";
        }
        int adminId = AuthenticationUtil.getAdminId();

        boardRequestDto.setAdminId(adminId);

        // 게시글 저장
        galleryBoardService.postGalleryBoard(boardRequestDto);

        int savedBoardId = boardRequestDto.getBoardId();

        // 게시글에 첨부된 파일 업로드
        MultipartFile[] files = boardRequestDto.getFiles();

        List<FileDto> fileDtoList =
                fileService.saveFilesWithThumbnail(files, savedBoardId);

        fileService.uploadFiles(fileDtoList);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/gallery/{boardId}")
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
     * 갤러리 게시글을 update
     *
     * @param boardId              the board id
     * @param boardSearchCondition 검색 조건
     * @param boardRequestDto      게시글 정보 Dto
     * @param bindingResult        검증오류 보관 객체
     * @param model                the model
     * @return 작성된 게시글 페이지로 redirect
     * @throws IOException the io exception
     */
    @PostMapping("/boards/gallery/{boardId}")
    public String updateFreeBoard(
            @PathVariable("boardId") int boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition,
            @Validated(Gallery.class)
            @ModelAttribute BoardRequestDto boardRequestDto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            List<CategoryDto> categoryList =
                    categoryService.getCategoryList(BoardType.GALLERY);

            List<FileDto> fileDtoList = fileService.getFileList(boardId);

            model.addAttribute("categoryList", categoryList);
            model.addAttribute("fileDtoList", fileDtoList);
            model.addAttribute("type", BoardType.GALLERY);
            model.addAttribute("formType", FormType.MODIFY);

            return "admin/views/writeView";
        }

        boardRequestDto.setBoardId(boardId);

        // 게시글 업데이트
        galleryBoardService.updateGalleryBoard(boardRequestDto);

        // 파일 삭제 적용
        fileService.deleteFiles(boardRequestDto.getDeleteFileIdList());

        // 파일 업로드 적용
        MultipartFile[] files = boardRequestDto.getFiles();

        List<FileDto> fileDtoList =
                fileService.saveFilesWithThumbnail(files, boardId);

        fileService.uploadFiles(fileDtoList);

        // 검색 조건을 유지시켜 작성된 글 상세보기 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/gallery/{boardId}")
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
     * 갤러리 게시글을 삭제하는 메서드
     *
     * @param boardId              게시글 Id
     * @param boardSearchCondition 검색 조건

     * @return 삭제 후 게시글 목록으로 이동
     */
    @GetMapping("/board/gallery/delete/{boardId}")
    public String deleteGalleryBoard(
            @PathVariable("boardId") int boardId,
            @ModelAttribute("boardSearch")
            BoardSearchCondition boardSearchCondition
    ) {
        // 게시글을 삭제
        galleryBoardService.deleteGalleryBoard(boardId);

        // 검색 조건을 유지시켜 게시글 리스트 페이지로 리다이렉트
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/admin/boards/gallery/")
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
     * 파일 다운로드
     *
     * @param fileId 파일 Id
     * @return 파일 file
     * @throws MalformedURLException the malformed url exception
     */
    @GetMapping("/board/gallery/files/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileId") int fileId)
            throws MalformedURLException {
        // 파일 조회
        FileDto fileDto = fileService.getFile(fileId);

        // 원본 이름과 저장된 이름 가져옴
        String originalName = fileDto.getOriginalName();

        String savedName = fileDto.getSavedName();

        //파일의 경로를 생성하여 UrlResource 생성
        UrlResource urlResource =
                new UrlResource("file:" + fileService.getFullPath(savedName));

        // 다운로드 파일명 인코딩
        String encodedOriginalName =
                UriUtils.encode(originalName, StandardCharsets.UTF_8);

        // Content-Disposition 헤더를 설정하여 다운로드할 파일의 이름을 지정
        String contentDisposition = "attachment; filename=\""
                + encodedOriginalName + "\"";

        // ResponseEntity를 사용하여 파일을 응답으로 반환
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
