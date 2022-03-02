package com.project.polaroid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPagingDTO {
    private Long goodsId;
    private String memberNickname;
    private String goodsTitle;
    private String goodsContents;
    private int goodsPrice;
    private List<GoodsPhotoDetailDTO> photoList;

}
