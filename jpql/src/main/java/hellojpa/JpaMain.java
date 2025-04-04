package hellojpa;

import hellojpa.jpql.Member;
import hellojpa.jpql.MemberType;
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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setAge(10);
            member1.setType(MemberType.ADMIN);
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            /**
             * 경로 표현식
             * - 실무에선 묵시적 조인을 사용하지 않는 것을 권장
             */
            String query = "select m.username from Member m"; // 1) 상태 필드
//            String query = "select m.team from Member m"; // 2) 단일 값 연관 경로 -> 묵시적 내부 조인 발생, 탐색 O
//            String query = "select t.members from Team t"; // 3) 컬렉션 값 연관 경로 -> 묵시적 내부 조인 발생, 탐색 X
//            String query = "select m from Team t join t.members m"; // 3-1) 탐색을 위한 명시적 조인

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            for (Member s : result) {
                System.out.println("s = " + s);
            }

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