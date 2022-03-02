package com.project.polaroid.service;

import com.project.polaroid.dto.MemberAddInfo;
import com.project.polaroid.entity.MemberEntity;

public interface MemberService {

    // 회원가입
    void memberSave(MemberEntity member);

    // oauth 로그인 추가정보(닉네임,전화번호,주소)
    void memberAddInfo(MemberAddInfo memberAddInfo,Long memberId);

    // 메일 중복체크
    String mailDuplicate(String mail);

    // 닉네임 중복체크
    String nicknameDuplicate(String nickname);

    // 멤버 정보 (마이페이지)
    MemberEntity findById(Long memberId);
}
