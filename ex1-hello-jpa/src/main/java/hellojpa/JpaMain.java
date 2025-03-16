package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager(); // EntityManager는 쓰레다 간의 공유 X!!

        /**
         * JPA의 모든 데이터 변경은 트랜잭션 안에서 실행됨
         */
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /**
             * 회원 등록
             */
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");

//            em.persist(member);

            /**
             * 회원 수정
             */
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");

            /**
             * JPQL로 전체 회원 검색
             */
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(5) // 페이징
//                    .setMaxResults(8)  // 페이징
                    .getResultList();
            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
