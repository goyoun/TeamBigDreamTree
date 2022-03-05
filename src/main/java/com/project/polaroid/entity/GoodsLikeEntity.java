package com.project.polaroid.entity;

import com.project.polaroid.dto.GoodsLikeSaveDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="goodsLike_table")
public class GoodsLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="goodsLike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="goods_id")
    private GoodsEntity goodsEntity;

    @ManyToOne(fetch = FetchType.LAZY) //LAZY 부하 ↓
    @JoinColumn(name = "member_id") // 부모테이블의 pk 컬럼이름
    private MemberEntity memberEntity;

    public static GoodsLikeEntity toGoodsLikeEntity(GoodsEntity goodsEntity, MemberEntity memberEntity) {
        GoodsLikeEntity goodsLikeEntity = new GoodsLikeEntity();
        goodsLikeEntity.setGoodsEntity(goodsEntity);
        goodsLikeEntity.setMemberEntity(memberEntity);
        return goodsLikeEntity;
    }
}

