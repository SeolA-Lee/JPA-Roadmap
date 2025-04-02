package hellojpa;

import hellojpa.jpql.Member;
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

            /**
             * TypeQuery, Query
             */
//            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            Query query3 = em.createQuery("select m.username, m.age from Member m"); // Query: 반환 타입이 명확하지 않음

            /**
             * 결과 조회 API: getResultList(), getSingleResult()
             */
//            List<Member> resultList = query1.getResultList(); // getResultList(): 결과가 없으면 빈 리스트 반환

//            TypedQuery<Member> noResultQuery = em.createQuery("select m from Member m", Member.class);
//            Member result = noResultQuery.getSingleResult();
//            System.out.println("result = " + result);

            /**
             * 파라미터 바인딩 - 이름 기준
             * 주의! 위치 기반은 웬만해선 사용하지 않는 것을 권장
             */
            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            System.out.println("result = " + result.getUsername());

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