package com.portfolio.communityuser.services;

import com.portfolio.communityuser.dtos.FileDto;
import com.portfolio.communityuser.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type File service.
 */
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    /**
     * fileRepository 의존성 주입
     */
    private final FileRepository fileRepository;

    /**
     * 파일 db에 정보를 저장하는 메서드
     *
     * @param fileDtoList 파일 Dto List
     * @throws IOException the io exception
     */
    @Transactional
    public void uploadFiles(List<FileDto> fileDtoList)
            throws IOException {
        for (FileDto fileDto : fileDtoList) {
            fileRepository.postFile(fileDto);
        }
    }

    /**
     * 파일을 지정된 폴더에 저장하고 정보를 List로 반환해주는 메서드
     *
     * @param files   파일들
     * @param boardId 게시글 Id
     * @return FileDto List
     * @throws IOException the io exception
     */
    public List<FileDto> saveFiles(MultipartFile[] files, int boardId) throws
            IOException {
        List<FileDto> savedFileList = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            if (multipartFile.getSize() != 0 &&
                    isAllowedFileType(multipartFile, true)
            ) {
                // 저장파일명을 만들어주어 폴더에 저장
                String originalName = multipartFile.getOriginalFilename();

                String savedName = getSavedName(originalName);

                multipartFile.transferTo(new File(getFullPath(savedName)));

                // db에 반영하기 위해 FileDto를 만들어 List에 담아줌
                FileDto fileDto = FileDto.builder()
                        .originalName(originalName)
                        .savedName(savedName)
                        .boardId(boardId)
                        .build();

                savedFileList.add(fileDto);
            }
        }

        return savedFileList;
    }

    private boolean isAllowedFileType(MultipartFile file, boolean allowZip) {
        String contentType = file.getContentType();

        if (contentType != null) {
            // 이미지 파일인지 체크
            if (contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                    contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
                    contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
                    contentType.equals(MediaType.IMAGE_GIF_VALUE)
            ) {
                return true;
            }

            // zip 파일인지 체크
            String extension = extractExtension(file.getOriginalFilename());

            if (allowZip && extension.equals("zip")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 파일을 지정된 폴더에 저장하고 정보를 List로 반환해주는 메서드
     * 썸네일도 함께 생성하여 저장
     *
     * @param files   파일들
     * @param boardId 게시글 Id
     * @return FileDto List
     * @throws IOException the io exception
     */
    public List<FileDto> saveFilesWithThumbnail(
            MultipartFile[] files,
            int boardId
    ) throws IOException {
        List<FileDto> savedFileList = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            if (multipartFile.getSize() != 0 &&
                    isAllowedFileType(multipartFile, false)
            ) {
                // 저장파일명을 만들어주어 폴더에 저장
                String originalName = multipartFile.getOriginalFilename();
                String savedName = getSavedName(originalName);
                String fullPath = getFullPath(savedName);

                // 원본 파일 저장
                multipartFile.transferTo(new File(fullPath));

                // 썸네일 생성 및 저장
                String thumbnailName = "t_" + savedName;
                String thumbnailFullPath = getFullPath(thumbnailName);

                Thumbnails.of(fullPath)
                        .size(30, 30)
                        .toFile(thumbnailFullPath);

                // db에 반영하기 위해 FileDto를 만들어 List에 담아줌
                FileDto fileDto = FileDto.builder()
                        .originalName(originalName)
                        .savedName(savedName)
                        .thumbnailName(thumbnailName)
                        .boardId(boardId)
                        .build();

                savedFileList.add(fileDto);
            }
        }

        return savedFileList;
    }

    /**
     * 파일 이름을 포함한 저장 경로를 가져오는 메서드
     *
     * @param fileName 저장 파일명
     * @return 저장 경로
     */
    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    /**
     * 원본 파일명에서 확장자를 얻어 UUID형태의 저장 파일명을 지정하주는 메서드
     *
     * @param originalName 원본 파일명
     * @return 저장시킬 파일명
     */
    private String getSavedName(String originalName) {
        String extension = extractExtension(originalName);

        return UUID.randomUUID().toString() + "." + extension;
    }

    /**
     * 원본 파일명에서 확장자를 추출하는 메서드
     *
     * @param originalName 원본 파일명
     * @return 확장자명
     */
    private String extractExtension(String originalName) {
        int index = originalName.lastIndexOf(".");

        return originalName.substring(index + 1);
    }

    /**
     * 게시글Id로 파일들을 가져오는 메서드
     *
     * @param boardId 게시글Id
     * @return 파일 List
     */
    public List<FileDto> getFileList(int boardId) {
        return fileRepository.getFileList(boardId);
    }

    /**
     * 파일Id로 파일 정보를 조회하는 메서드
     *
     * @param fileId 파일 Id
     * @return the file
     */
    public FileDto getFile(int fileId) {
        return fileRepository.getFile(fileId);
    }

    /**
     * 게시글 Id로 썸네일을 조회하는 메서드
     *
     * @param boardId 파일 Id
     * @return the file
     */
    public FileDto getThumbnail(int boardId) {
        return fileRepository.getThumbnail(boardId);
    }

    /**
     * file들을 삭제하는 메서드
     * @param deleteFileIdList 지울 FileId List
     */
    public void deleteFiles(List<Integer> deleteFileIdList) {
        for (int fileId : deleteFileIdList) {
            fileRepository.deleteFile(fileId);
        }
    }
}

