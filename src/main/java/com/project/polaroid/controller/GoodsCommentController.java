package com.project.polaroid.controller;

import com.project.polaroid.dto.CommentSaveDTO;
import com.project.polaroid.dto.GoodsCommentSaveDTO;
import com.project.polaroid.service.CommentService;
import com.project.polaroid.service.GoodsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/goodsComment/*")
public class GoodsCommentController {

    private final GoodsCommentService gcs;

    @GetMapping("save")
    public String save(@ModelAttribute GoodsCommentSaveDTO goodsCommentSaveDTO) {
        Long goodsCommentId = gcs.save(goodsCommentSaveDTO);
        return null;
    }
}
