package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    public void 상품주문() throws Exception{
        //조건
        Member member = new Member("kim", 20);
        member.setAddress(new Address("태평양", "해협", "12345"));
        memberService.join(member);

        Category category = createCategory();

        Item book = createBook(category);


        //실행
        Long orderId = orderService.order(member.getId(), book.getId(), 1);
        Order getOrder = orderRepository.findOne(orderId);

        //검증 (expected, actual, message)
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품의 주문 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 갯수 확인");
        assertEquals(12000, getOrder.getTotalPrice(), "주문 전체 금액 확인");
        assertEquals(99, book.getStockQuantity(), "재고확인");
    }


    @Test
    @Transactional
    public void 재고수량초과(){
        //조건
        Member member = createMember();

        Category category = createCategory();

        Item book = createBook(category);

        //실행
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), 11));
        //검증
    }

    private Category createCategory() {
        Category category = new Category("여행");
        categoryService.save(category);
        return category;
    }

    private Member createMember() {
        Member member = new Member("kim", 30);
        memberService.join(member);
        return member;
    }

    private Item createBook(Category category) {
        Item book = Book.builder().name("how to do")
                .writer("kim")
                .stockQuantity(100)
                .price(12000)
                .category(category)
                .build();
        itemService.saveItem(book);
        return book;
    }

    @Test
    @Transactional
    public void 주문취소() throws Exception{
        //조건
        Member member = createMember();
        Category category = createCategory();
        Item book = createBook(category);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), 50);
        //실행
        orderService.cancel(orderId);
        //검증
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문상태 확인  : 취소");
        assertEquals(100, book.getStockQuantity(),"재고수량 원복 : 100");

    }
}