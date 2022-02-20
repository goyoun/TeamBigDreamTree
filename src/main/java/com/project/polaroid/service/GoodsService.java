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

    Long save(GoodsSaveDTO goodsSaveDTO);

    void saveFile(Long goodsId, MultipartFile g) throws IOException;

    List<GoodsDetailDTO> search(GoodsSearchDTO goodsSearchDTO);

//    List<BoardDetailDTO> search(GoodsSearchDTO goodsSearchDTO);
}
