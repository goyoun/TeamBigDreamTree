package com.project.polaroid.service;

import com.project.polaroid.dto.GoodsCommentDetailDTO;
import com.project.polaroid.dto.GoodsCommentSaveDTO;
import com.project.polaroid.entity.*;
import com.project.polaroid.repository.GoodsCommentRepository;
import com.project.polaroid.repository.GoodsRepository;
import com.project.polaroid.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsCommentServiceImpl implements GoodsCommentService{
    private final GoodsCommentRepository gcr;
    private final GoodsRepository gr;
    private final MemberRepository mr;

    // 굿즈 코멘트 세이브
    @Override
    public Long save(GoodsCommentSaveDTO goodsCommentSaveDTO) {
        System.out.println("댓글이 넘어오나요?3");
        GoodsEntity goodsEntity = gr.findById(goodsCommentSaveDTO.getGoodsId()).get();
        MemberEntity memberEntity = mr.findById(goodsCommentSaveDTO.getMemberId()).get();
        GoodsCommentEntity goodsCommentEntity = GoodsCommentEntity.toGoodsCommentEntity(goodsCommentSaveDTO, goodsEntity, memberEntity);
        Long goodsCommentId = gcr.save(goodsCommentEntity).getId();
        return goodsCommentId;
    }

    @Override
    public List<GoodsCommentDetailDTO> findAll(Long goodsId) {
        System.out.println("댓글이 넘어오나요?4");
        GoodsEntity goodsEntity = gr.findById(goodsId).get();
        List<GoodsCommentEntity> goodsCommentEntityList = goodsEntity.getGoodsCommentEntityList();
        List<GoodsCommentDetailDTO> goodsCommentList = GoodsCommentDetailDTO.toGoodsCommentDetailDTOList(goodsCommentEntityList);
        return goodsCommentList;
    }

    @Override
    public void deleteById(Long goodsCommentId) {
        System.out.println("삭제가되나요");
        gcr.deleteById(goodsCommentId);

    }
}
