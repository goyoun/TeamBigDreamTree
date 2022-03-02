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
    @Column(name="goodscomment_id")
    private Long id;
    @Column
    private String goodsCommentWriter;
    @Column
    private String goodsCommentContents;


    // 원글의 게시글 번호를 참조하기 위한 설정 댓글:게시글 N:1
    // 보드 엔티티 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id") // 부모테이블(참조하고자하는 테이블)의 pk 컬럼이름
    private BoardEntity boardEntity; // 참조하고자 하는 테이블을 관리하는 엔티티

    // 회원 엔티티 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

//    public static GoodsCommentEntity toSaveEntity(GoodsCommentSaveDTO goodsCommentSaveDTO, BoardEntity boardEntity, MemberEntity memberEntity) {
//        GoodsCommentEntity goodsCommentEntity = new GoodsCommentEntity();
//        goodsCommentEntity.setGoodsCommentWriter(memberEntity.getMemberEmail());
//        goodsCommentEntity.setGoodsCommentContents(goodsCommentSaveDTO.get());
//
//        goodsCommentEntity.setBoardEntity(boardEntity);
//        goodsCommentEntity.setMemberEntity(memberEntity);
//        return goodsCommentEntity;
//    }


    public static GoodsCommentEntity toGoodsCommentEntity(GoodsCommentSaveDTO goodsCommentSaveDTO) {
        GoodsCommentEntity goodsCommentEntity = new GoodsCommentEntity();
        goodsCommentEntity.setGoodsCommentContents(goodsCommentSaveDTO.getGoodsCommentContents());
        return goodsCommentEntity;
    }
}
