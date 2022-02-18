package com.project.polaroid.repository;

import com.project.polaroid.entity.BoardEntity;
import com.project.polaroid.entity.GoodsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {

//    // BoardEntity가 맞는건가요? 네 보드에서  boardid 정보를 가져와서 goodsid 랑
//    // 그럼 조회하고자 하는 테이블이 뭔데요? 여기는 GoodsEntity를 조회하는 내용이어야지.
//    // 조회하고자하는 테이블은 board 테이블이긴한데
//    // 그럼  BoardRepository에서 해야죠. 지금 오류내용이 그런 내용인듯..
//    // 수정해보겠습니다 감사합니다!
//    Page<BoardEntity> findAll(PageRequest id);
//
//    List<BoardEntity> findByBoardWriterContaining(String search);
//
//    List<BoardEntity> findByBoardTitleContaining(String search);
}
