package hellojpa;

import hellojpa.jpql.Member;
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

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            /* 조인 */
//            String query = "select m from Member m inner join m.team t"; // 1) 내부 조인 (inner 생략 가능)
//            String query = "select m from Member m left outer join m.team t"; // 2) 외부 조인 (outer 생략 가능)
//            String query = "select m from Member m, Team t where m.username = t.name"; // 3) 세타 조인

            /* 조인 - ON절 */
//            String query = "select m from Member m left join m.team t on t.name = 'teamA'"; // 1) 조인 대상 필터링
            String query = "select m from Member m left join Team t on m.username = t.name"; // 2) 연관관계 없는 엔티티 외부 조인
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("result = " + result.size());

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