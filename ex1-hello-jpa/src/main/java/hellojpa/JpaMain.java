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

            Member member = new Member();
//            member.setId(1L);
//            member.setUsername("A");
//            member.setRoleType(RoleType.USER);

//            member.setId(2L);
//            member.setUsername("B");
//            member.setRoleType(RoleType.ADMIN);

            member.setId(3L);
            member.setUsername("C");
            member.setRoleType(RoleType.GUEST);

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
