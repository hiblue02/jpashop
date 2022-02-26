package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column
    private int quantity;
    @Column
    private int orderPrice;

    /** 비즈니스 로직**/
    public void cancel() {
        getItem().addStock(quantity);
    }

    public int getTotalPrice(){
        return orderPrice*quantity;
    }
    /** 생성메소드 **/
    public static OrderItem createOrderItem(Item item, int orderPrice, int quantity){
        OrderItem orderItem = new OrderItem()   ;
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setQuantity(quantity);
        item.removeStock(quantity);
        return orderItem;
    }

}