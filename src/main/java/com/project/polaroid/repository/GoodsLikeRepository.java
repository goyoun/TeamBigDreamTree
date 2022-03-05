package com.project.polaroid.repository;

import com.project.polaroid.entity.GoodsEntity;
import com.project.polaroid.entity.GoodsLikeEntity;
import com.project.polaroid.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GoodsLikeRepository extends JpaRepository<GoodsLikeEntity, Long> {

    GoodsLikeEntity findByGoodsEntityAndMemberEntity(GoodsEntity goodsEntity, MemberEntity memberEntity);

    void deleteByGoodsEntityAndMemberEntity(GoodsEntity goodsEntity, MemberEntity memberEntity);

    List<GoodsLikeEntity> findAllByMemberEntity(MemberEntity memberEntity);


//    @Query(value = "SELECT count(a) FROM GoodsLikeEntity a WHERE a.goodsEntity.id= :goodsId and a.memberEntity.id =:memberId ")
//    public int goodsLikeCount(Long goodsId,Long memberId);
    
}
