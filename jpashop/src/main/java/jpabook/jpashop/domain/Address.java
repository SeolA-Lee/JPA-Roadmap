package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 어딘가 내장이 될 수 있음
@Getter // 값 타입을 변경 불가능하게 설계해야 하므로 @Setter X
public class Address {

    private String city;
    private String street;
    private String zipcode;

    /**
     * JPA 스펙 상 엔티티나 임베디드 타입(@Embeddable)은 자바 기본 생성자를 public 또는 protected로 설정해야 함
     * protected로 설정하는 것이 더 안전함
     */
    protected Address() {
    }

    /**
     * 생성자에서 값을 모두 초기화해서 변경 불가능하도록 함
     */
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
