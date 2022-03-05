package com.project.polaroid.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.project.polaroid.dto.GoodsSaveDTO;
import com.project.polaroid.dto.GoodsUpdateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
    private String goodsInFor;
    @OneToMany(mappedBy = "goodsEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsPhotoEntity> GoodsPhotoEntity = new ArrayList<>();

    @OneToMany(mappedBy = "goodsId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsCommentEntity> goodsCommentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "goodsEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsLikeEntity> goodsLikeEntityList = new ArrayList<>();


    // 멤버엔티티 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity goodsWriter;

    public static GoodsEntity toGoodsEntitySave(GoodsSaveDTO goodsSaveDTO, MemberEntity memberEntity) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsWriter(memberEntity);
        goodsEntity.setGoodsContents(goodsSaveDTO.getGoodsContents());
        goodsEntity.setGoodsTitle(goodsSaveDTO.getGoodsTitle());
        goodsEntity.setGoodsPrice(goodsSaveDTO.getGoodsPrice());
        goodsEntity.setGoodsStock(goodsSaveDTO.getGoodsStock());
        goodsEntity.setGoodsInFor(goodsSaveDTO.getGoodsInFor());
        goodsEntity.setGoodsView(0);
        return goodsEntity;
    }

    public static GoodsEntity toUpdateGoodsEntity(GoodsUpdateDTO goodsUpdateDTO, MemberEntity memberEntity) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setId(goodsUpdateDTO.getGoodsId());
        goodsEntity.setGoodsWriter(memberEntity);
        goodsEntity.setGoodsContents(goodsUpdateDTO.getGoodsContents());
        return goodsEntity;
    }
}