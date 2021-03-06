package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;
    private QOrder order ;
    private QMember member ;
    private JPAQuery<Order> query;

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public void save(Order order){
        em.persist(order);
    }

    //queryD
    public List<Order> findAllByString(OrderSearch orderSearch){

        order = QOrder.order;
        member = QMember.member;
        query = new JPAQuery<>(em);

        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEqual(orderSearch.getOrderStatus()),
                        nameLike(orderSearch.getMemberName()))
                .limit(100)
                .fetch();
    }

    private BooleanExpression nameLike(String memberName) {
        if(!StringUtils.hasText(memberName)){
            return null;
        }
        return order.member.name.contains(memberName);
    }

    private BooleanExpression statusEqual(OrderStatus orderStatus) {
        if(orderStatus == null){
            return null;
        }
        return order.status.eq(orderStatus);
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery("select o from Order o "
                            +"join fetch o.member m "
                            +"join fetch o.delivery d", Order.class).getResultList();
    }

    public List<Order> findAllWithItem(){
        return em.createQuery("select distinct o from Order o " +
                " join fetch o.orderItems oi" +
                " join fetch oi.item i"+
                " join fetch o.member m" +
                " join fetch o.delivery d", Order.class).getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery("select o from Order o "
                +"join fetch o.member m "
                +"join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

}
