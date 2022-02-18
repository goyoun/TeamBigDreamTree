package com.project.polaroid.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "gphoto_table")
public class GoodsPhotoEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "gphoto_id")
        private Long id;

        @ManyToOne
        @JoinColumn(name = "goods_id")
        private GoodsEntity goodsEntity;

        @Column
        private String goodsFilename;

    }

