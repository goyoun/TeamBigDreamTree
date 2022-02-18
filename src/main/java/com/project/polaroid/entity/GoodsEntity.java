package com.project.polaroid.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.project.polaroid.dto.GoodsSaveDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "goods_table")
public class GoodsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @Column
    private String goodsWriter;

    @Column
    private String goodsTitle;

    @Column
    private String goodsContents;

    @Column
    private int goodsStock;

    @Column
    private int goodsPrice;

    @Column
    private int goodsView;

    @Column
    private int goodsLike;

    @OneToMany(mappedBy = "goodsEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsPhotoEntity> GoodsPhotoEntity = new ArrayList<>();

    public static GoodsEntity toGoodsEntitySave(GoodsSaveDTO goodsSaveDTO) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsWriter(goodsSaveDTO.getGoodsWriter());
        goodsEntity.setGoodsContents(goodsSaveDTO.getGoodsContents());
        goodsEntity.setGoodsTitle(goodsSaveDTO.getGoodsTitle());
        goodsEntity.setGoodsPrice(goodsSaveDTO.getGoodsPrice());
        goodsEntity.setGoodsLike(0);
        goodsEntity.setGoodsView(0);

        return goodsEntity;
    }
}
