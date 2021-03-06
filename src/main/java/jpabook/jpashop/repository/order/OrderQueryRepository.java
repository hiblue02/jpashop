package jpabook.jpashop.repository.order;

import jpabook.jpashop.repository.order.dto.OrderFlatDto;
import jpabook.jpashop.repository.order.dto.OrderItemQueryDto;
import jpabook.jpashop.repository.order.dto.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findAllByDto_optimization(){
        List<OrderQueryDto> orders = findOrders();

        List<Long> orderIds = orders.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

        List<OrderItemQueryDto> orderItems
                = em.createQuery("select new jpabook.jpashop.repository.order.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.quantity)" +
                        "   from OrderItem oi" +
                        "   join oi.item i" +
                        "   where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream().collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

       orders.forEach(o->{o.setOrderItems(orderItemMap.get(o.getOrderId()));});

       return orders;

    }

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> orders = findOrders();
        orders.forEach(o->{
           o.setOrderItems(findOrderItems(o.getOrderId()));
        });

        return orders;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.repository.order.dto.OrderItemQueryDto(i.name, oi.orderPrice, oi.quantity)" +
                "   from OrderItem oi" +
                "   join oi.item i" +
                "   where oi.order.id=:orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery("select new jpabook.jpashop.repository.order.dto.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                "   from Order o" +
                "   join o.member m" +
                "   join o.delivery d", OrderQueryDto.class).getResultList();
    }

}
