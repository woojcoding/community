package com.portfolio.community.controller;

import com.portfolio.community.dto.FileDto;
import com.portfolio.community.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

/**
 * The type File controller.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/files")
public class FileController {

    /**
     * 파일에 대한 로직을 처리하는 fileService를 의존성 주입
     */
    private final FileService fileService;

    /**
     * 파일 다운로드
     *
     * @param fileId 파일 Id
     * @return 파일 file
     * @throws MalformedURLException the malformed url exception
     */
    @GetMapping("/{fileId}")
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
