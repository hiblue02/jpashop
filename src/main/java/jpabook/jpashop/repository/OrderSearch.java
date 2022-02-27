package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;

@Getter
@Setter
public class OrderSearch {
    String memberName;
    private OrderStatus orderStatus;
}
