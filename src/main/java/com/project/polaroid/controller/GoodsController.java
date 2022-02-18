package com.project.polaroid.controller;

import com.project.polaroid.common.PagingConst;
import com.project.polaroid.dto.*;
import com.project.polaroid.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/goods/*")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService gs;

//    // 판매자 페이지 출력
//    @GetMapping("{boardId}")
//    public String goodsForm(@PathVariable String boardId) {
//
//        return "index";
//    }

    // 굿즈보드 출력
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        // Page 객체 // 서비스로 넘기는건 pageable 객체를 넘긴다
        Page<GoodsPagingDTO> goodsList = gs.paging(pageable);
        model.addAttribute("goodsList", goodsList);
        // 삼항연산자로 바꿈
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < goodsList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : goodsList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "goods/paging";
    }

    // 굿즈보드 상세조회
    @GetMapping("{goodsId}")
    public String findById(@PathVariable Long goodsId, Model model) {
        //리턴 받아오기
        GoodsDetailDTO goods = gs.findById(goodsId);
        // 모델에 담아서 넘기기
        model.addAttribute("goods", goods);
        model.addAttribute("goodsComment", goods.getGoodsCommentList());
        return "goods/findById";
    }

    //굿즈보드  작성페이지
    @GetMapping("save")
    public String saveForm() {
        return "goods/save";
    }

    // 굿즈보드 작성
    @PostMapping("save")
    public String save(@ModelAttribute GoodsSaveDTO goodsSaveDTO) throws IOException {
        Long goodsId = gs.save(goodsSaveDTO);
        for (MultipartFile g: goodsSaveDTO.getGoodsFile()) {
            gs.saveFile(goodsId, g);
        }
        return "redirect:/goods/";
    }

    //굿즈보드 구매페이지
    @GetMapping("buy")
    public String buyForm() {
        return "goods/buy";
    }



//    //굿즈보드 서치
//    @GetMapping("search")
//    public String search(@ModelAttribute GoodsSearchDTO goodsSearchDTO, Model model) {
//        // 검색결과를 가지고 가야하니까 Model
//        List<BoardDetailDTO> goodsDetailDTOList = gs.search(goodsSearchDTO);
//        model.addAttribute("boardList", goodsDetailDTOList);
//        return "/goods/search";


        // 굿즈보드 본인게시글 확인

        // 굿즈보드 구매페이지 요청

        // 굿즈보드 구매


//    }
}






