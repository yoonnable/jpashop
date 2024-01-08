package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
@RunWith(SpringRunner.class)
@SpringBootTest // 스프링 컨테이너 안에서 테스트 돌리기 위함
@Transactional // 테스트 한 것들 롤백처리
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
//    @Rollback(false) // 롤백이 기본으로 되는거라 커밋하려면 이 속성 넣어주기
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush(); // 롤백처리가 되어도, 영속성 컨텍스트에 있는 변경 내용을 DB에 반영해준다.
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        // when
        memberService.join(member1);
        // try ~ catch 문을 Test 주석 속성으로 간결하게 구현
//        try {
//            memberService.join(member2); // 예외가 발생해야 한다!!
//        } catch (IllegalStateException e) { // 예상 예외를 캐치해서 테스트 성공시키기
//            return;
//        }
        memberService.join(member2); // 예외가 발생해야 한다!!

        // then
        fail("예외가 발생해야 한다.");
    }

}