package com.project.polaroid.service;

import com.project.polaroid.common.PagingConst;
import com.project.polaroid.dto.*;
import com.project.polaroid.entity.GoodsEntity;
import com.project.polaroid.entity.GoodsPhotoEntity;
import com.project.polaroid.repository.GoodsPhotoRepository;
import com.project.polaroid.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final GoodsRepository gr;
    private final GoodsPhotoRepository gpr;
    private final MemberService ms;

    // 페이징
    @Override
    public Page<GoodsPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page - 1;
        page = (page == 1) ? 0 : (page - 1);
        //                        몇페이지? / 몇개씩 볼껀지       / 무슨 기준으로 정렬할지 (내림차순)/ 기준 컬럼 (Entity 필드이름) /
        Page<GoodsEntity> goodsEntities = gr.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Entity는 서비스 밖으로 나가면 안됨
        // Page<BoardEntity> => Page(BoardPagingDTO) 로 변환시켜야하지만 페이징은 안된다.
        Page<GoodsPagingDTO> goodsList = goodsEntities.map(
                // 엔티티 객체를 담기위한 반복용 변수 goods
                goods -> new GoodsPagingDTO(
                        goods.getId(),
                        goods.getGoodsWriter().getMemberNickname(),
                        goods.getGoodsTitle(),
                        goods.getGoodsContents(),
                        goods.getGoodsPrice(),
                        GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))
        );
        return goodsList;
    }

    // 디테일
    @Override
    @Transactional
    public GoodsDetailDTO findById(Long goodsId) {

        Optional<GoodsEntity> optionalGoodsEntity = gr.findById(goodsId);
        GoodsDetailDTO goodsDetailDTO=null;
        if(optionalGoodsEntity.isPresent()) {
            GoodsEntity goodsEntity = optionalGoodsEntity.get();
            goodsDetailDTO = GoodsDetailDTO.toGoodsDetailDTO(goodsEntity);
        }
        return goodsDetailDTO;
    }

    // 글쓰기 기능
    @Override
    public Long save(GoodsSaveDTO goodsSaveDTO, Long memberId) {
        GoodsEntity goodsEntity = GoodsEntity.toGoodsEntitySave(goodsSaveDTO, ms.findById(memberId));
        Long goodsId = gr.save(goodsEntity).getId();
        return goodsId;
    }

    // 파일저장 기능
    @Override
    public void saveFile(Long goodsId, MultipartFile goodsFile) throws IOException {
        String goodsFilename = goodsFile.getOriginalFilename();
        goodsFilename = System.currentTimeMillis() + "-" + goodsFilename;
        String savePath = "C:\\Development\\source\\springboot\\Polaroid\\src\\main\\resources\\static\\goodsFile\\" + goodsFilename;
        if (!goodsFile.isEmpty()) {
            goodsFile.transferTo(new File(savePath));
        }
        GoodsPhotoEntity goodsPhotoEntity = new GoodsPhotoEntity();
        goodsPhotoEntity.setGoodsEntity(gr.findById(goodsId).get());
        goodsPhotoEntity.setGoodsFilename(goodsFilename);
        gpr.save(goodsPhotoEntity);

    }


    @Override
    public Page<GoodsPagingDTO> search(GoodsSearchDTO goodsSearchDTO, Pageable pageable) {
        if(goodsSearchDTO.getSelect().equals("goodsTitle")){
            Page<GoodsEntity> goodsEntityList = gr.findByGoodsTitleContaining(goodsSearchDTO.getSearch(), PageRequest.of(pageable.getPageNumber()-1, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
            Page<GoodsPagingDTO> goodsList = goodsEntityList.map(
                    goods -> new GoodsPagingDTO(
                            goods.getId(),
                            goods.getGoodsWriter().getMemberNickname(),
                            goods.getGoodsTitle(),
                            goods.getGoodsContents(),
                            goods.getGoodsPrice(),
                            GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))
            );
            return goodsList;
        } else {
            Page<GoodsEntity> goodsEntities =  gr.searchWriter(goodsSearchDTO.getSearch(), PageRequest.of(pageable.getPageNumber() - 1, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
            Page<GoodsPagingDTO> goodsList = goodsEntities.map(
                    goods -> new GoodsPagingDTO(
                            goods.getId(),
                            goods.getGoodsWriter().getMemberNickname(),
                            goods.getGoodsTitle(),
                            goods.getGoodsContents(),
                            goods.getGoodsPrice(),
                            GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))

            );
            return goodsList;
        }
    }

//    @Override
//    public Page<GoodsPagingDTO> searchPage(String select, String search, Pageable pageable) {
//        if(select.equals("goodsTitle")){
//            Page<GoodsEntity> goodsEntityList = gr.findByGoodsTitleContaining(search, PageRequest.of(pageable.getPageNumber()-1, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
//            Page<GoodsPagingDTO> goodsList = goodsEntityList.map(
//                    goods -> new GoodsPagingDTO(
//                            goods.getId(),
//                            goods.getGoodsWriter().getMemberNickname(),
//                            goods.getGoodsTitle(),
//                            goods.getGoodsContents(),
//                            goods.getGoodsPrice(),
//                            GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))
//            );
//            return goodsList;
//        } else {
//            Page<GoodsEntity> goodsEntities =  gr.searchWriter(search, PageRequest.of(pageable.getPageNumber() - 1, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
//            Page<GoodsPagingDTO> goodsList = goodsEntities.map(
//                    goods -> new GoodsPagingDTO(
//                            goods.getId(),
//                            goods.getGoodsWriter().getMemberNickname(),
//                            goods.getGoodsTitle(),
//                            goods.getGoodsContents(),
//                            goods.getGoodsPrice(),
//                            GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))
//
//            );
//            return goodsList;
//        }
//    }

    // 굿즈 내글 리스트
    @Override
    @Transactional
    public Page<GoodsPagingDTO> list(Long memberId, Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1) ? 0 : (page - 1);
        //                        몇페이지? / 몇개씩 볼껀지       / 무슨 기준으로 정렬할지 (내림차순)/ 기준 컬럼 (Entity 필드이름) /
        Page<GoodsEntity> goodsDetailDTO = gr.findByIdGoodsWriter(memberId, PageRequest.of(pageable.getPageNumber() - 1, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<GoodsPagingDTO> goodsList = goodsDetailDTO.map(
                goods -> new GoodsPagingDTO(
                        goods.getId(),
                        goods.getGoodsWriter().getMemberNickname(),
                        goods.getGoodsTitle(),
                        goods.getGoodsContents(),
                        goods.getGoodsPrice(),
                        GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))

        );
        return goodsList;
    }



}

