package hellojpa;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 조인 전략 (기본)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략 (단순하면 이 전략으로)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // (추천 X) 구현 클래스마다 테이블 전략
@DiscriminatorColumn // [조인전략] 권장! DTYPE이 생기고, 자식 엔티티명이 들어가게 됨
public abstract class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
