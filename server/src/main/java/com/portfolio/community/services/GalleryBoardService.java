package com.portfolio.community.services;

import com.portfolio.community.dtos.BoardDto;
import com.portfolio.community.enums.BoardType;
import com.portfolio.community.repositories.BoardSearchCondition;
import com.portfolio.community.repositories.GalleryBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Board service.
 */
@Service
@RequiredArgsConstructor
public class GalleryBoardService {

    /**
     * boardRepository 의존성 주입
     */
    private final GalleryBoardRepository galleryBoardRepository;

    /**
     * 게시글 목록 조회에서 검색 조건에 따라 게시글 정보들을 List로 받도록
     * Repository에 요청하기 위해 사용하는 메서드
     *
     * @param boardSearchCondition 검색 조건
     * @return BoardListDto 게시글 정보 List
     */
    public List<BoardDto> getGalleryBoardList(
            BoardSearchCondition boardSearchCondition) {
        boardSearchCondition.setType(BoardType.GALLERY);

        int pageNum = boardSearchCondition.getPageNum();

        int pageSize = boardSearchCondition.getPageSize();

        int offset = (pageNum - 1) * pageSize;

        boardSearchCondition.setOffSet(offset);

        return galleryBoardRepository.getBoardList(boardSearchCondition);
    }

    /**
     * 검색조건에 따른 게시글 수를 조회하는 메서드
     *
     * @param boardSearchCondition 검색조건
     * @return int 조회수
     */
    public int getTotalBoardCount(BoardSearchCondition boardSearchCondition) {
        return galleryBoardRepository.getTotalBoardCount(boardSearchCondition);
    }

    /**
     * 갤러리 게시글을 업데이트하는 메서드
     *
     * @param boardDto 게시글 정보
     */
    public void updateGalleryBoard(BoardDto boardDto) {
        boardDto.setType(BoardType.GALLERY);

        galleryBoardRepository.updateGalleryBoard(boardDto);
    }

    /**
     * 갤러리 게시글을 작성하는 메서드
     *
     * @param boardDto
     */
    public void postGalleryBoard(BoardDto boardDto) {
        boardDto.setType(BoardType.GALLERY);

        galleryBoardRepository.postGalleryBoard(boardDto);
    }

    /**
     * 게시글Id로 갤러리 게시글을 가져오는 메서드
     *
     * @param boardId
     * @return boardDto 게시글 정보
     */
    public BoardDto getGalleryBoard(int boardId) {
        return galleryBoardRepository.getGalleryBoard(boardId);
    }

    /**
     * 게시글을 삭제하는 메서드
     *
     * @param boardId
     */
    public void deleteGalleryBoard(int boardId) {
        galleryBoardRepository.deleteGalleryBoard(boardId);
    }
}
