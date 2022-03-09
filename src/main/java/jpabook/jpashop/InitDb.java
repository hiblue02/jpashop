package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService ;

    @PostConstruct
    public void init(){
        initService.init1();
        initService.init2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void init1(){
            Member userA = new Member();
            userA.setName("userA");
            userA.setAddress(new Address("서울", "경희대로","1234"));
            em.persist(userA);

            Book JPA1 = new Book("JPA1", 100, 25000, "mr.kim");
            Book JPA2 = new Book("JPA2", 100, 30000, "mr.kim");
            em.persist(JPA1);
            em.persist(JPA2);

            OrderItem orderItem1 = OrderItem.createOrderItem(JPA1, JPA1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(JPA2, JPA2.getPrice(), 1);
            Delivery delivery = new Delivery();
            delivery.setAddress(userA.getAddress());
            Order order = Order.createOrder(userA, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void init2(){
            Member userB = new Member();
            userB.setName("userB");
            userB.setAddress(new Address("서울", "경복궁로", "1122"));
            em.persist(userB);

            Book SPRING1 = new Book("SPRING1", 1000, 10000, "hello.world");
            Book SPRING2 = new Book("SPRING2", 1000, 12000, "hello.world");
            em.persist(SPRING1);
            em.persist(SPRING2);

            OrderItem orderItem1 = OrderItem.createOrderItem(SPRING1, SPRING1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(SPRING2, SPRING1.getPrice(), 1);
            Delivery delivery = new Delivery();
            delivery.setAddress(userB.getAddress());
            Order order = Order.createOrder(userB, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
