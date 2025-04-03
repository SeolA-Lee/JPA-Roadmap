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

            String query = "select (select avg(m1.age)from Member m1) as avgAge from Member m left join Team t on m.username = t.name"; // SELECT절 서브쿼리
//            String query = "select mm.age, mm.username " +
//                            "from (select m.age, m.username from Member m) as mm"; // FROM절 서브쿼리 -> 하이버네이트6부터 지원함

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