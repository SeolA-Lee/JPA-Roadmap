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

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            /**
             * JPQL 기본 함수
             */
//            String query = "select concat('a', 'b') from Member m"; // CONCAT (String으로 반환)
//            String query = "select substring(m.username, 2, 3) from Member m"; // SUBSTRING (String으로 반환)
//            String query = "select locate('de', 'abcdefg') from Member m"; // LOCATE (Integer로 반환)
//            String query = "select size(t.members) from Team t"; // SIZE (Integer로 반환)

            /**
             * 사용자 정의 함수 호출
             */
//            String query = "select function('group_concat', m.username) from Member m"; // 사용법 1
            String query = "select group_concat(m.username) from Member m"; // 사용법 2

            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

//            List<Integer> result = em.createQuery(query, Integer.class)
//                    .getResultList();

            for (String s : result) {
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