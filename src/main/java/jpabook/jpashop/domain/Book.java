package jpabook.jpashop.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    private String writer;


    @Builder
    public Book(@NonNull String name, @NonNull int stockQuantity, @NonNull int price, String writer) {
        this.setName(name);
        this.setStockQuantity(stockQuantity);
        this.setPrice(price);
        this.writer = writer;
    }

}