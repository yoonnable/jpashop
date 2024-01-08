package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Beam 으로 등록 (Component하위)
@RequiredArgsConstructor // spring boot JPA가 엔티티 매니저 주입하는걸 이걸로도 할 수 있게 지원해 준다
public class MemberRepository {

//    @PersistenceContext
    private final EntityManager em; // 엔티티 매니저 주입

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) //jpql사용(엔티티 객체를 이용해 조회함), 2번째 파라미터 : 반환 타입
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em. createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
