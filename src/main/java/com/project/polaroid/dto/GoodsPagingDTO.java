package com.project.polaroid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPagingDTO {
    private Long goodsId;
    private String goodsWriter;
    private String goodsTitle;
    private String goodsContents;
    private int goodsPrice;

}
