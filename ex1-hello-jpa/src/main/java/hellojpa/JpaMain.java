package hellojpa;

import jakarta.persistence.*;
import org.h2.engine.User;

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

            /* 비영속 상태 */
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");

            /* 영속 상태 */
//            System.out.println("=== BEFORE ===");
//            em.persist(member); // 이때 DB에 저장되는 것은 아님 -> 1차 캐시에 저장되는 것임
//            System.out.println("=== AFTER ===");

//            Member findMember = em.find(Member.class, 100L);

//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());

            /**
             * 영속성 컨텍스트 이점
             * 1. 1차 캐시에서 조회
             * 2. 동일성 보장
             */
//            Member findMember1 = em.find(Member.class, 101L); // 처음 조회할 때 영속성 컨텍스트에 올려 놓음 -> 쿼리 O
//            Member findMember2 = em.find(Member.class, 101L); // 1차 캐시에서 조회하기 때문에 쿼리 X

//            System.out.println("result = " + (findMember1 == findMember2));

            /**
             * 3. 트랜잭션을 지원하는 쓰기 지연
             */
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");

//            em.persist(member1);
//            em.persist(member2);

            /**
             * 4. 변경 감지
             */
//            Member member = em.find(Member.class, 150L);
//            member.setName("ZZZZZ");

            /**
             * flush - 직접 호출
             */
//            Member member = new Member(200L, "member200");
//            em.persist(member);

//            em.flush(); // DB에 Insert 쿼리가 즉시 날라감

            /**
             * 준영속 상태
             */
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

//            em.detach(member); // 영속성 컨텍스트에서 더 이상 관리하지 않게 됨 -> tx.commit() 해도 아무 일도 일어나지 않음

            em.clear(); // em에 있는 영속성 컨텍스트를 통으로 다 지워버림 (초기화)
            Member member2 = em.find(Member.class, 150L); // 다시 영속성 컨텍스트에 올리게 됨

            System.out.println("===========================");

            tx.commit(); // 이 시점에 쿼리가 날라감
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
