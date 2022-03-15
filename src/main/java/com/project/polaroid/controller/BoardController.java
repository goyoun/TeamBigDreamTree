package com.project.polaroid.controller;

import com.project.polaroid.auth.PrincipalDetails;
import com.project.polaroid.dto.BoardDetailDTO;
import com.project.polaroid.dto.BoardPagingDTO;
import com.project.polaroid.dto.BoardSaveDTO;
import com.project.polaroid.dto.BoardUpdateDTO;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.service.BoardService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

    private final BoardService bs;
    private final MemberService memberService;

    @GetMapping
    public String main(@PageableDefault(page = 1) Pageable pageable, HttpSession session, Model model) {
        Page<BoardPagingDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList", boardList);

        if(session.getAttribute("LoginNumber") != null) {
            MemberEntity member = memberService.findById((Long) session.getAttribute("LoginNumber"));
            System.out.println("member = " + member);
            model.addAttribute("member", member);
            return "board/main";
        }
        else
            return "board/main";
    }

    @GetMapping("save")
    public String saveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        MemberEntity member=memberService.findById(principalDetails.getMember().getId());
        System.out.println("member = " + member);
        model.addAttribute("member", member);
        return "board/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO) throws IOException {
        System.out.println("boardSaveDTO = " + boardSaveDTO);
        System.out.println("boardSaveDTO.getMemberId() = " + boardSaveDTO.getMemberId());
        Long boardId = bs.save(boardSaveDTO);
        for (MultipartFile b: boardSaveDTO.getBoardFile()) {
            bs.saveFile(boardId, b);
        }
        return "redirect:/board/";
    }

    @GetMapping("{boardId}")
    public String findById(@PathVariable Long boardId, Model model, HttpSession session) {
        System.out.println("삼세조회");
        System.out.println("boardId = " + boardId);
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);

        Long memberId = (Long)session.getAttribute("LoginNumber");
        int likeStatus = bs.findLike(boardId, memberId);
        System.out.println("likeStatus = " + likeStatus);
        model.addAttribute("like",likeStatus);


        System.out.println("boardDetailDTO.getPhoto() = " + boardDetailDTO.getPhoto());
        model.addAttribute("board", boardDetailDTO);
        model.addAttribute("imageSize", boardDetailDTO.getPhoto().size());
        model.addAttribute("commentList", boardDetailDTO.getCommentList());
        System.out.println("boardDetailDTO.getCommentList() = " + boardDetailDTO.getCommentList());
        System.out.println("boardDetailDTO.getPhoto().size() = " + boardDetailDTO.getPhoto().size());
        return "board/findById";
    }

    @GetMapping("search/{keyword}")
    public String search(@PathVariable String keyword, @PageableDefault(page =1) Pageable pageable, Model model) {
        keyword = "#"+keyword;
        System.out.println("keyword = " + keyword);
        Page<BoardPagingDTO> boardList = bs.search(keyword, pageable);
        model.addAttribute("keyword", keyword);

        model.addAttribute("boardList", boardList);

        return "board/search";
    }

    @PostMapping("like")
    public @ResponseBody int like(Long boardId, Long memberId) {
        System.out.println("좋아요");
        int result = bs.saveLike(boardId,memberId);
        return result;
    }

    @GetMapping("update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model) {
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        model.addAttribute("board", boardDetailDTO);
        return "board/update";
    }

    @PutMapping("{boardId}")
    public ResponseEntity update(@RequestBody BoardUpdateDTO boardUpdateDTO) {
        System.out.println("boardUpdateDTO = " + boardUpdateDTO);
        bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{boardId}")
    public ResponseEntity deleteById(@PathVariable Long boardId) {
        bs.deleteById(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
