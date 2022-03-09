package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.simpleQuery.OrderSimpleQueryRepository;
import jpabook.jpashop.repository.simpleQuery.dto.OrderSimpleQueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple/orders")
    public List<Order> getOrders(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getStatus();
        }

        return all;
    }

    @GetMapping("/api/v2/simple/orders")
    public Result getOrdersV2(){
        List<SimpleOrderDto> collect = orderRepository.findAllByString(new OrderSearch())
                .stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        public SimpleOrderDto(Order order){
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @GetMapping("/api/v3/simple/orders")
    public Result getOrdersV3(){
        List<SimpleOrderDto> collect = orderRepository.findAllWithMemberDelivery()
                .stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/v4/simple/orders")
    public Result getOrdersV4(){
        List<OrderSimpleQueryDto> collect = orderSimpleQueryRepository.findOrderDtos() ;
        return new Result(collect);
    }
}
