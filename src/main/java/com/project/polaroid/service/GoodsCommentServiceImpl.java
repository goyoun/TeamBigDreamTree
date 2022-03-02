package com.project.polaroid.service;

import com.project.polaroid.dto.GoodsCommentSaveDTO;
import com.project.polaroid.entity.CommentEntity;
import com.project.polaroid.entity.GoodsCommentEntity;
import com.project.polaroid.repository.GoodsCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsCommentServiceImpl implements GoodsCommentService{
    private final GoodsCommentRepository gcr;

    // 굿즈 코멘트 세이브
    @Override
    public Long save(GoodsCommentSaveDTO goodsCommentSaveDTO) {
        GoodsCommentEntity goodsCommentEntity = GoodsCommentEntity.toGoodsCommentEntity(goodsCommentSaveDTO);
        Long goodsCommentId = gcr.save(goodsCommentEntity).getId();
        return null;
    }
}
