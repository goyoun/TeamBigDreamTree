package com.project.polaroid.service;

import com.project.polaroid.dto.GoodsCommentDetailDTO;
import com.project.polaroid.dto.GoodsCommentSaveDTO;

import java.util.List;

public interface GoodsCommentService {
    Long save(GoodsCommentSaveDTO goodsCommentSaveDTO);

    List<GoodsCommentDetailDTO> findAll(Long goodsId);

    void deleteById(Long goodsCommentId);
}
