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

            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            member.setTeam(team); // 단방향 연관관계 설정, 참조 저장
            /**
             * 연관관계 편의 메소드 방법 1
             */
//            member.changeTeam(team);
            em.persist(member);

            /**
             * 연관관계 편의 메소드 방법 2
             */
            team.addMember(member);

            /**
             * 이 줄과 em.flush(), clear()를 주석처리하면 iter 안의 내용이 출력되지 않음
             * 따라서 양방향 매핑 시 양쪽으로 값을 세팅해줘야 함
             */
//            team.getMembers().add(member); // -> 연관관계 편의 메소드로 생성함

            /**
             * 아래처럼 em.flush(); em.clear(); 를 해야
             * 1차 캐시가 아닌, DB에서 깔끔하게 값을 불러올 수 있음
             */
//            em.flush();
//            em.clear();


            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시
            List<Member> members = findTeam.getMembers();

            System.out.println("======================");
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("======================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
