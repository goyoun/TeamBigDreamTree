package com.project.polaroid.dto;

import com.project.polaroid.entity.BoardEntity;
import com.project.polaroid.entity.GoodsCommentEntity;
import com.project.polaroid.entity.GoodsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailDTO {
    private Long GoodsId;
    private Long memberId;
    private String goodsWriter;
    private String goodsTitle;
    private String goodsContents;
    private int goodsPrice;
    private String goodsFilename;
    private LocalDateTime goodsDate;
    private List<GoodsCommentDetailDTO> goodsCommentList;
    private int goodsView;
    private int goodsLike;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private List<GoodsPhotoDetailDTO> goodsPhoto;

    public static GoodsDetailDTO toGoodsDetailDTO(GoodsEntity goodsEntity) {
        GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
        goodsDetailDTO.setGoodsId(goodsEntity.getId());
        goodsDetailDTO.setGoodsWriter(goodsEntity.getGoodsWriter());
        goodsDetailDTO.setGoodsTitle(goodsEntity.getGoodsTitle());
        goodsDetailDTO.setGoodsContents(goodsEntity.getGoodsContents());
        goodsDetailDTO.setGoodsView(goodsEntity.getGoodsView());
        goodsDetailDTO.setGoodsLike(goodsEntity.getGoodsLike());
        goodsDetailDTO.setCreateTime(goodsEntity.getCreateTime());
        goodsDetailDTO.setUpdateTime(goodsEntity.getUpdateTime());
        goodsDetailDTO.setGoodsPhoto(GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goodsEntity.getGoodsPhotoEntity()));
            return goodsDetailDTO;
        }

    public static List<GoodsDetailDTO> toChangeDTOList(List<GoodsEntity> goodsEntityList) {
        List<GoodsDetailDTO> goodsDetailDTOList = new ArrayList<>();
        for (GoodsEntity m: goodsEntityList) {
            goodsDetailDTOList.add(toGoodsDetailDTO(m));
        }
        return goodsDetailDTOList;
    }

}
