package com.project.polaroid.controller;

import com.project.polaroid.auth.PrincipalDetails;
import com.project.polaroid.common.PagingConst;
import com.project.polaroid.dto.*;
import com.project.polaroid.repository.MemberRepository;
import com.project.polaroid.service.GoodsService;
import com.project.polaroid.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/goods/*")
@RequiredArgsConstructor
public class GoodsController {

    private final MemberService ms;
    private final GoodsService gs;
    private final MemberRepository mr;

    // 굿즈보드 페이징
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable,Model model,HttpSession session) {
//        @AuthenticationPrincipal PrincipalDetails principalDetails
//        model.addAttribute("member", principalDetails.getMember().getId());
        // Page 객체 // 서비스로 넘기는건 pageable 객체를 넘긴다
        Page<GoodsPagingDTO> goodsList = gs.paging(pageable);
        model.addAttribute("goodsList", goodsList);
        Long memberId = (Long) session.getAttribute("LoginNumber");
        model.addAttribute("memberId", memberId);
        // 삼항연산자로 바꿈
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < goodsList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : goodsList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "goods/paging";
    }

    @GetMapping("search")
    public String search(@PageableDefault(page =1) Pageable pageable,
                         @ModelAttribute GoodsSearchDTO goodsSearchDTO,
                         Model model) {
        Page<GoodsPagingDTO> goodsDetailDTOList = gs.search(goodsSearchDTO, pageable);
        model.addAttribute("goodsList", goodsDetailDTOList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < goodsDetailDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : goodsDetailDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("select", goodsSearchDTO.getSelect());
        model.addAttribute("search", goodsSearchDTO.getSearch());
        return "goods/search";
    }

    // 굿즈보드 상세조회
    @GetMapping("{goodsId}")
    public String findById(@PathVariable("goodsId") Long goodsId, Model model,HttpSession session) {
        GoodsDetailDTO goods = gs.findById(goodsId);

        Long memberId = (Long) session.getAttribute("LoginNumber");
        model.addAttribute("member",ms.findById(memberId));

        model.addAttribute("goods", goods);
        model.addAttribute("goodsCommentList", goods.getGoodsCommentList());

        int likeStatus = gs.findLike(goodsId, memberId);
        model.addAttribute("like",likeStatus);
        System.out.println("likeStatus = " + likeStatus);

        gs.viewUp(goodsId);

        return "goods/findById";
    }

    // 좋아요
    @PostMapping("goodsLike")
    public @ResponseBody int like (Long goodsId, Long memberId) {
        System.out.println("좋아요가 넘어오나요");
        int result = gs.saveLike(goodsId,memberId);

        return result;
    }

    //굿즈보드  작성페이지
    @GetMapping("save")
    public String saveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("member", principalDetails.getMember());
        return "goods/save";
    }

    // 굿즈보드 작성
    @PostMapping("save")
    public String save(@ModelAttribute GoodsSaveDTO goodsSaveDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {
        Long goodsId = gs.save(goodsSaveDTO, principalDetails.getMember().getId());
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


    // 굿즈보드 본인게시글 확인
    @GetMapping("list/{memberId}")
    public String myList (@PathVariable Long memberId,
                          @ModelAttribute GoodsDetailDTO goodsDetailDTO, Model model,
                          @PageableDefault(page = 1) Pageable pageable) {
        Page<GoodsPagingDTO> goodsDetailDTOList = gs.list(memberId, pageable);
        String memberNickname = mr.findById(memberId).get().getMemberNickname();
        model.addAttribute("goodsList", goodsDetailDTOList);
        model.addAttribute("memberNickname", memberNickname);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < goodsDetailDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : goodsDetailDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("memberId", memberId);
        return "/goods/myList";

    }

    // 결제
    @PostMapping("pay")
    public @ResponseBody String pay(@RequestParam ("goodsId") Long goodsId,
                                    @RequestParam ("count") int count){
        gs.pay(goodsId,count);
        return "ok";
    }

    // 업데이트폼
    @GetMapping("update/{goodsId}")
    public String updateForm(@PathVariable Long goodsId, Model model) {
        GoodsDetailDTO goodsDetailDTO = gs.findById(goodsId);
        model.addAttribute("goods", goodsDetailDTO);
        return "goods/update";
    }

    // 글업데이트
    @PutMapping("{goodsId}")
    public ResponseEntity update(@RequestBody GoodsUpdateDTO goodsUpdateDTO) {
        Long goodsId = gs.update(goodsUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 글삭제
    @DeleteMapping("{goodsId}")
    public String deleteById(@PathVariable Long goodsId) {
        gs.deleteById(goodsId);
        return "redirect:/goods/";
    }

    // 찜목록
    @GetMapping("pick/{memberId}")
    public String pick(@PathVariable Long memberId, Model model ) {
        List<GoodsDetailDTO> goodsDetailDTO = gs.pick(memberId);
        model.addAttribute("pick", goodsDetailDTO);
        return "goods/pick";
    }

}





