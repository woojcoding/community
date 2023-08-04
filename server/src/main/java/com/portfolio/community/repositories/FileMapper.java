package com.portfolio.community.repositories;

import com.portfolio.community.dtos.FileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * The interface File mapper.
 */
@Mapper
public interface FileMapper {

    /**
     * 파일 목록을 반환해주는 메서드
     *
     * @param boardId 게시글 Id
     * @return List<File>     파일 List
     */
    List<FileDto> getFileList(int boardId);

    /**
     * 파일 정보를 db에 저장하는 메서드
     *
     * @param fileDto 파일 Dto
     */
    void postFile(FileDto fileDto);

    /**
     * 파일 Id로 파일 정보를 조회하는 메서드
     *
     * @param fileId 파일 Id
     * @return the file
     */
    FileDto getFile(int fileId);

    /**
     * 파일 Id에 해당하는 파일을 삭제하는 메서드
     *
     * @param fileId 파일 Id
     */
    void deleteFile(int fileId);

    /**
     * 게시글 Id에 해당하는 file들을 삭제하는 메서드
     *
     * @param boardId 게시글 Id
     */
    void deleteFilesByBoardId(int boardId);
}
