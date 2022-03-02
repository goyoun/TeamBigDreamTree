package com.project.polaroid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GoodsCommentSaveDTO {
    private Long goodsId;
    private String goodsCommentWriter;
    private String goodsCommentContents;
}
