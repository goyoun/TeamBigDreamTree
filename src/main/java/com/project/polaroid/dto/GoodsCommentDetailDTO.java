package com.project.polaroid.dto;

import com.project.polaroid.entity.GoodsCommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCommentDetailDTO {
    private Long goodsCommentId;
    private Long boardId;
    private String goodsCommentWriter;
    private String goodsCommentContents;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static GoodsCommentDetailDTO toGoodsCommentDetailDTO(GoodsCommentEntity goodsCommentEntity) {
        // 객체생성
        GoodsCommentDetailDTO goodsCommentDetailDTO = new GoodsCommentDetailDTO();
        // 옮겨담기
        goodsCommentDetailDTO.setGoodsCommentId(goodsCommentEntity.getId());
        goodsCommentDetailDTO.setGoodsCommentWriter(goodsCommentEntity.getGoodsCommentWriter());
        goodsCommentDetailDTO.setGoodsCommentContents(goodsCommentEntity.getGoodsCommentWriter());
        goodsCommentDetailDTO.setCreateTime(goodsCommentEntity.getCreateTime());
        goodsCommentDetailDTO.setUpdateTime(goodsCommentEntity.getUpdateTime());
        goodsCommentDetailDTO.setBoardId(goodsCommentEntity.getBoardEntity().getId());
        return goodsCommentDetailDTO;
    }

    public static List<GoodsCommentDetailDTO> toCommentDetailDTOList(List<GoodsCommentEntity> goodsCommentEntityList) {
        List<GoodsCommentDetailDTO> goodsCommentDetailDTOList = new ArrayList<>();
        for (GoodsCommentEntity c: goodsCommentEntityList) {
            goodsCommentDetailDTOList.add(toGoodsCommentDetailDTO(c));
        }
        return goodsCommentDetailDTOList;
    }
}