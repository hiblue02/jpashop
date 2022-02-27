package jpabook.jpashop.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {
    @Setter
    private String writer;



    @Builder
    public Book(String name, int stockQuantity, int price, Category category, String writer) {
        this.setName(name);
        this.setStockQuantity(stockQuantity);
        this.setPrice(price);
        this.addCategory(category);
        this.writer = writer;
    }

}