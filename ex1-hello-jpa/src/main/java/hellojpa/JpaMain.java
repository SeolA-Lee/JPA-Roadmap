package hellojpa;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager(); // EntityManager는 쓰레드 간의 공유 X!!

        /**
         * JPA의 모든 데이터 변경은 트랜잭션 안에서 실행됨
         */
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /**
             * JPQL 특징
             * 1) 테이블이 아닌 엔티티를 대상으로 검색함
             * 2) String이기 때문에 동적 쿼리를 해결하기 힘듦
             */
//            String qlString = "select m from Member m where m.username like '%kim%'";

//            List<Member> result = em.createQuery(
//                    qlString,
//                    Member.class
//            ).getResultList();

//            for (Member member : result) {
//                System.out.println("member = " + member);
//            }

            /**
             * Criteria 특징
             * 1) 문자가 아닌 자바 코드로 JPQL 작성 가능
             * 2) 동적 쿼리 조금 더 해결 가능
             * 하지만! 너무 복잡하고(SQL스럽지 않음) 실용성이 없기 때문에 QueryDSL 사용을 권장함
             */
            /* 사용 준비 */
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            /* 루트 클래스 (조회를 시작할 클래스) */
//            Root<Member> m = query.from(Member.class);

            /* 쿼리 생성 */
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
//            List<Member> resultList = em.createQuery(cq).getResultList();

            /**
             * QueryDSL 특징
             * 1) 문자가 아닌 자바 코드로 JPQL 작성 가능
             * 2) 동적 쿼리 작성이 편리함
             * 3) 단순하고 쉬움
             * 따라서! 실무 사용 권장
             */
            // 간단한 예제: 섹션 11 - 41강 20분쯤 참고

            /**
             * 네이티브 SQL 특징
             * 1) JPA가 제공하는 SQL을 직접 사용 가능
             */
//            em.createNativeQuery("select MEMBER_ID, city, street, zipcode, USERNAME from MEMBER")
//                            .getResultList();


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
