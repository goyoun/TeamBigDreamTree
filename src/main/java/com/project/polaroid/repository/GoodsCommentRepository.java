package com.project.polaroid.repository;

import com.project.polaroid.entity.GoodsCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface GoodsCommentRepository extends JpaRepository<GoodsCommentEntity, Long>{

}
