package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MemberProduct { // Order 같은 테이블이라고 보면 됨

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int count;
    private int price;

    private LocalDateTime orderDateTime;
}
