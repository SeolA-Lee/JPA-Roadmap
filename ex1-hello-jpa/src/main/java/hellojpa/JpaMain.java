package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

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

//            Member member = new Member();
//            member.setUsername("hello");

//            em.persist(member);

//            em.flush();
//            em.clear();

            /* 1차 캐시에 아무것도 남지 않음 */
//            Member findMember = em.getReference(Member.class, member.getId()); // 여기서 쿼리가 나가지 않음
//            System.out.println("findMember = " + findMember.getClass()); // Proxy 클래스

//            System.out.println("findMember.id = " + findMember.getId()); // 여긴 파라미터값에서 가져와서 DB 쿼리 X
//            System.out.println("findMember.username = " + findMember.getUsername()); // 실제로 사용할 시점에서 쿼리가 나감
//            System.out.println("findMember.username = " + findMember.getUsername()); // 두 번째 요청이므로 이미 초기화 되어 있는 Proxy 이기에 쿼리 X

            /**
             * 프록시 특징 예제
             */
            Member member1 = new Member();
            member1.setUsername("hello1");
            em.persist(member1);

//            Member member2 = new Member();
//            member2.setUsername("hello2");
//            em.persist(member2);

            em.flush();
            em.clear();

            /**
             * 1. 프록시 객체는 원본 엔티티를 상속 받음
             *    타입 체크 시 '==' 비교 X, instance of 사용
             */
//            Member m1 = em.find(Member.class, member1.getId());
//            Member m2 = em.getReference(Member.class, member2.getId());

//            logic(m1, m2);

            /**
             * 2. 영속성 컨텍스트에 찾는 엔티티가 이미 있으면
             *    em.getReference()를 호출해도 실제 엔티티 반환
             */
//            Member m1 = em.find(Member.class, member1.getId());
//            System.out.println("m1 = " + m1.getClass());

//            Member reference = em.getReference(Member.class, member1.getId());
//            System.out.println("reference = " + reference.getClass());

//            System.out.println("a == a: " + (m1 == reference));

            /* 반대도 성립 */
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember = " + refMember.getClass());

//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getClass());

//            System.out.println("refMember == findMember: " + (refMember == findMember));

            /**
             * 3. 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때,
             *    프록시를 초기화 하면 문제 발생
             */
            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass()); // Proxy

//            em.detach(refMember); // 더 이상 영속성 컨텍스트에서 관리 X
//            em.clear();

//            refMember.getUsername(); // detach 또는 clear 시 Error 발생

//            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); // 초기화 여부 확인

            Hibernate.initialize(refMember); // 강제 초기화

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(Member m1, Member m2) {
//        System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass())); // 둘 다 em.find()로 찾을 땐 true 하지만 getReference()는 false
        System.out.println("m1 == m2: " + (m1 instanceof Member));
        System.out.println("m1 == m2: " + (m2 instanceof Member));
    }
}
