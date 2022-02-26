package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //이걸해야 끝나고 롤백이 가능함.
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입(){
        //조건
        Member member = new Member("kim", 10);
        //실행
        Long createdMemberId = memberService.join(member);
        //검증
        assertEquals(member, memberRepository.findOne(createdMemberId));

    }

    @Test
    @DisplayName("회원가입_중복테스트")
    public void 회원가입_중복테스트(){
        
        //조건
        Member member1 = new Member("kim", 10);
        Member member2 = new Member("kim", 10);
        //실행
        memberService.join(member1);

        //검증
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}