package com.project.polaroid.service;

import com.project.polaroid.dto.BoardDetailDTO;
import com.project.polaroid.dto.BoardPagingDTO;
import com.project.polaroid.dto.BoardSaveDTO;
import com.project.polaroid.dto.BoardUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Long save(BoardSaveDTO boardSaveDTO);

    void deleteById(Long boardId);

    Long update(BoardUpdateDTO boardUpdateDTO);

    Page<BoardPagingDTO> paging(Pageable pageable);

    void saveFile(Long boardId, MultipartFile boardFile) throws IOException;

    BoardDetailDTO findById(Long boardId);

    Page<BoardPagingDTO> search(String keyword, Pageable pageable);

    int findLike(Long boardId, Long memberId);

    int saveLike(Long boardId, Long memberId);

//    List<BoardDetailDTO> findByTag(String keyword);
}