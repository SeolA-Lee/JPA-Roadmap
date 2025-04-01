package hellojpa;

public class ValueMain {

    public static void main(String[] args) {

        /**
         * 동일성(identity) 비교: 인스턴스의 참조 값을 비교, == 사용
         */
        int a = 10;
        int b = 10;

        System.out.println("a == b: " + (a == b)); // true

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");

        System.out.println("address1 == address2: " + (address1 == address2)); // false -> 참조값이 다르기 때문

        /**
         * 동등성(equivalence) 비교: 인스턴스의 값을 비교, equals() 사용
         * 기본 equals()는 '==' 비교이기 때문에, Address 클래스에서 재정의 해야 함
         */
        System.out.println("address1 equals address2: " + (address1.equals(address2)));
    }
}
