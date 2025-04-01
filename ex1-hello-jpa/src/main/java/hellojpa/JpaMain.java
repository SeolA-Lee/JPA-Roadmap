package hellojpa;

import jakarta.persistence.*;

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

            Address address = new Address("city", "street", "10000");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            em.persist(member);

            // 복사하지 않으면 Embedded 되어 있기 때문에 member1, member2 둘 다 바뀌게 됨
//            Address copyAddress = address; // 주의! 객체 타입은 참조를 전달하기 때문에 이렇게 copy 하면 안 됨
//            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setHomeAddress(copyAddress);
//            em.persist(member2);

//            member.getHomeAddress().setCity("newCity"); // setter가 private로 설정되면 변경 불가해짐

            /**
             * setter가 private 일 때 값 변경 방법
             * : 완전히 새로 갈아끼워야 함
             */
            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);

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
