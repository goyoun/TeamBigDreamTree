package com.project.polaroid.service;


import com.project.polaroid.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GoodsService {

    Page<GoodsPagingDTO> paging(Pageable pageable);

    GoodsDetailDTO findById(Long goodsId);

    Long save(GoodsSaveDTO goodsSaveDTO, Long memberId);

    void saveFile(Long goodsId, MultipartFile g) throws IOException;


    Page<GoodsPagingDTO> list(Long memberId, Pageable pageable);

    // 서치기능
//    List<GoodsDetailDTO> search(GoodsSearchDTO goodsSearchDTO);

    Page<GoodsPagingDTO> search(GoodsSearchDTO goodsSearchDTO, Pageable pageable);

//    Page<GoodsPagingDTO> searchPage(String select, String search, Pageable pageable);
}
