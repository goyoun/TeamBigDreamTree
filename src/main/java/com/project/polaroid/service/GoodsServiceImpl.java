package com.project.polaroid.service;

import com.project.polaroid.common.PagingConst;
import com.project.polaroid.dto.*;
import com.project.polaroid.entity.BoardEntity;
import com.project.polaroid.entity.GoodsEntity;
import com.project.polaroid.entity.GoodsPhotoEntity;
import com.project.polaroid.entity.PhotoEntity;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final GoodsRepository gr;
    private final GoodsPhotoRepository gpr;


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
                // 엔티티 객체를 담기위한 반복용 변수 board
                goods -> new GoodsPagingDTO(
                        goods.getId(),
                        goods.getGoodsWriter(),
                        goods.getGoodsTitle(),
                        goods.getGoodsContents(),
                        goods.getGoodsPrice())
        );
        return goodsList;
    }

    @Override
    @Transactional
    public GoodsDetailDTO findById(Long goodsId) {
        /*
            1. MemberRepository로 부터 해당 회원의 정보를 MemberEntity로 가져옴.
            2. MemberEntity를 MemberDetailDTO로 바꿔서 컨트롤러로 리턴.
         */
        // 1.
        // Optional = null 방지
        Optional<GoodsEntity> optionalGoodsEntity = gr.findById(goodsId);
        /*
            Optional 객체 메서드
            - isPresnet(): 데이터가 있으면 true, 없으면 false 반환
            - isEmpty(); 데이터가 없으면 true, 있으면 false 반환
            - get(): Optional 객체에 들어있는 실제 데이터를 가져올때
         */
        GoodsDetailDTO goodsDetailDTO=null;
        if(optionalGoodsEntity.isPresent()) {
            GoodsEntity goodsEntity = optionalGoodsEntity.get();
            goodsDetailDTO = GoodsDetailDTO.toGoodsDetailDTO(goodsEntity);
        }
//        // .get() 은 옵셔널안에 Entity를 꺼냄
//        BoardEntity boardEntity = optionalBoardEntity.get();
//        // 2.
//        BoardDetailDTO boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);
        return goodsDetailDTO;
        // 위의 4줄을 밑에 한줄로도 가능하다.
        //return MemberDetailDTO.toMemberDetailDTO(mr.findById(memberId).get())

    }

    @Override
    public Long save(GoodsSaveDTO goodsSaveDTO) {
        GoodsEntity goodsEntity = GoodsEntity.toGoodsEntitySave(goodsSaveDTO);
        Long goodsId = gr.save(goodsEntity).getId();
        return goodsId;
    }

    @Override
    public void saveFile(Long goodsId, MultipartFile goodsFile) throws IOException {
        String goodsFilename = goodsFile.getOriginalFilename();
        goodsFilename = System.currentTimeMillis() + "-" + goodsFilename;
        String savePath = "C:\\Development\\source\\springboot\\Polaroid\\src\\main\\resources\\static\\goodsFile\\"+goodsFilename;
        if (!goodsFile.isEmpty()) {
            goodsFile.transferTo(new File(savePath));
        }
        GoodsPhotoEntity goodsPhotoEntity = new GoodsPhotoEntity();
        goodsPhotoEntity.setGoodsEntity(gr.findById(goodsId).get());
        goodsPhotoEntity.setGoodsFilename(goodsFilename);
        gpr.save(goodsPhotoEntity);
    }


//    @Override
//    public List<BoardDetailDTO> search(GoodsSearchDTO goodsSearchDTO) {
//        // 검색타입이 2가지니까 분리한다
//        if (goodsSearchDTO.getSelect().equals("boardWriter")) {
//            // Containing 은 %검색어% 앞뒤로 알맞는 단어가 있으면 검색이 된다.
//            List<BoardEntity> boardEntityList = gr.findByBoardWriterContaining(goodsSearchDTO.getSearch());
//            // DTO 타입으로 변환
//            List<BoardDetailDTO> boardDetailDTOList = BoardDetailDTO.toChangeDTOList(boardEntityList);
//            System.out.println("boardDetailDTOList = " + boardDetailDTOList);
//            return boardDetailDTOList;
//        } else {
//            List<BoardEntity> boardEntityList = gr.findByBoardTitleContaining(goodsSearchDTO.getSearch());
//            // DTO 타입으로 변환
//            List<BoardDetailDTO> boardDetailDTOList = BoardDetailDTO.toChangeDTOList(boardEntityList);
//            return boardDetailDTOList;
//        }
//    }
}

