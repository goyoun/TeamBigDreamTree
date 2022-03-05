package com.project.polaroid.entity;


import com.project.polaroid.dto.GoodsCommentSaveDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="goodscomment_table")
public class GoodsCommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="goodsComment_id")
    private Long id;
    @Column
    private String goodsCommentContents;

    // 원글의 게시글 번호를 참조하기 위한 설정 댓글:게시글 N:1
    // 보드 엔티티 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="goods_id") // 부모테이블(참조하고자하는 테이블)의 pk 컬럼이름
    private GoodsEntity goodsId; // 참조하고자 하는 테이블을 관리하는 엔티티

    // 회원 엔티티 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberId;

//    public static GoodsCommentEntity toSaveEntity(GoodsCommentSaveDTO goodsCommentSaveDTO, GoodsEntity goodsEntity, MemberEntity memberEntity) {
//        GoodsCommentEntity goodsCommentEntity = new GoodsCommentEntity();
//        goodsCommentEntity.setGoodsId(goodsEntity);
//        goodsCommentEntity.setMemberId(memberEntity);
//        goodsCommentEntity.setGoodsCommentContents(goodsCommentSaveDTO.getGoodsCommentContents());
//        return goodsCommentEntity;
//    }


    public static GoodsCommentEntity toGoodsCommentEntity(GoodsCommentSaveDTO goodsCommentSaveDTO, GoodsEntity goodsEntity, MemberEntity memberEntity) {
        GoodsCommentEntity goodsCommentEntity = new GoodsCommentEntity();

        goodsCommentEntity.setGoodsCommentContents(goodsCommentSaveDTO.getGoodsCommentContents());
        goodsCommentEntity.setGoodsId(goodsEntity);
        goodsCommentEntity.setMemberId(memberEntity);
        return goodsCommentEntity;
    }
}
