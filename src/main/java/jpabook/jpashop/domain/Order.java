package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter @Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column
    private LocalDateTime orderDate;

    public void setMember(Member member) {
        this.member = member;
        this.member.getOrders().add(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        this.delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    /**  생성 메서드 **/
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /** 비즈니스 로직 **/
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.FINISH){
            throw new IllegalStateException("이미 배송 완료된 상품을 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        this.orderItems.forEach(a->a.cancel());
    }

    /** 조회 로직 **/
    public int getTotalPrice(){
        return this.orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

}
