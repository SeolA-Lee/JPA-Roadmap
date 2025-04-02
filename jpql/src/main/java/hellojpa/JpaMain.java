package hellojpa;

import hellojpa.jpql.Address;
import hellojpa.jpql.Member;
import hellojpa.jpql.MemberDTO;
import hellojpa.jpql.Team;
import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            /**
             * 엔티티 프로젝션
             * SELECT m FROM Member m
             */
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();

//            Member findMember = result.get(0);
//            findMember.setAge(20);

            /**
             * 엔티티 프로젝션
             * SELECT m.team FROM Member m
             * -> SELECT t FROM Member m join m.team 처럼 명시적으로 join을 표현하는 것이 좋음
             */
//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
//                    .getResultList();

            /**
             * 임베디드 타입 프로젝션
             * SELECT o.address FROM Order o
             * 주의! SELECT a FROM Address a 처럼 실행할 수는 없음 (소속되어 있기 때문)
             */
//            em.createQuery("select o.address from Order o", Address.class)
//                    .getResultList();

            /**
             * 스칼라 타입 프로젝션
             * SELECT m.username, m.age FROM Member m
             */
//            em.createQuery("select distinct m.username, m.age from Member m")
//                    .getResultList();

            /* 여러 값 조회 방법 - new 명령어로 조회 */
            List<MemberDTO> result = em.createQuery("select new hellojpa.jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();                               // 주의! 패키지명을 포함한 전체 클래스명 입력

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}