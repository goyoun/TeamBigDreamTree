package com.project.polaroid.repository;

import com.project.polaroid.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository <MemberEntity,Long> {

    public MemberEntity findByMemberEmail(String email);
    public MemberEntity findByMemberNickname(String nickname);


    // oauth 추가정보 입력
    @Modifying
    @Query("update MemberEntity p set p.memberAddress = :address , p.memberPhone = :phone , p.memberNickname = :nickname where p.id = :id")
    void addInfo(String address, String phone,String nickname,Long id);

    // 비밀번호 변경
    @Modifying
    @Query("update MemberEntity p set p.memberPw = :memberPw where p.id = :id")
    void updateMember(String memberPw,Long id);




}