package jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /**
             * 양방향 매핑 시
             */
            Order order = new Order();
            order.addOrderItem(new OrderItem()); // 연관관계 편의 메소드 사용

            /**
             * 단방향으로만 매핑 시
             */
//            Order order = new Order();
//            em.persist(order);

//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);

//            em.persist(orderItem);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}