package hellojpa;

import jakarta.persistence.*;

import java.util.List;

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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamB);
            em.persist(member2);


            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member1.getId());

//            System.out.println("m = " + m.getTeam().getClass()); // 지연로딩 시 Proxy로 가져 옴

//            System.out.println("===============");
//            System.out.println("teamName = " + m.getTeam().getName()); // 지연로딩 시 초기화
//            System.out.println("===============");

            /**
             * 즉시로딩 사용 시 JPQL에서 N+1 문제를 일으킴
             * SQL: select * from Member; -- EAGER이므로 Team 테이블도 불러옴
             * SQL: select * from Team where TEAM_ID = xxx;
             */
//            List<Member> members = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();

            /**
             * 기본 해결방안
             * (1) LAZY로 다 바꿈
             * (2) fetch 조인 사용
             */
            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();

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
