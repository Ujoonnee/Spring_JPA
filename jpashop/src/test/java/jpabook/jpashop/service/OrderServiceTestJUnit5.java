package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTestJUnit5 {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired ItemService itemService;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);
        int orderAmount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderAmount);

        //then
        Order order = orderRepository.findOne(orderId);
        assertThat(book.getStockQuantity()).isEqualTo(itemService.findOne(book.getId()).getStockQuantity())
                                           .isSameAs(10 - orderAmount);
        assertThat(order.getTotalPrice()).isEqualTo(book.getPrice() * orderAmount);
        assertThat(OrderStatus.ORDER).isEqualTo(order.getStatus());
    }
    
    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        
        //when
        
        //then
        
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);

        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("화원1");
        member.setAddress(new Address("서울", "강남", "123-123"));
        em.persist(member);

        return member;
    }
}