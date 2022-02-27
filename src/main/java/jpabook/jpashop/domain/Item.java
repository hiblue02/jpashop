package jpabook.jpashop.domain;

import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "item_id", nullable = false)
    private Long id;
    @Column
    @Setter
    private String name;
    @Column
    @Setter
    private int stockQuantity;
    @Column
    @Setter
    private int price;
    @ManyToMany(mappedBy = "items")
    @Setter
    private List<Category> categories = new java.util.ArrayList<>();


    public void addCategory(Category category){
        this.categories.add(category);
        category.addItems(this);
    }

    /// 비즈니스 로직
    public void removeStock(int quantity){
        int remainStock = this.stockQuantity - quantity;
        if(remainStock < 0){
            throw new NotEnoughStockException("재고가 충분하지 않습니다.");
        }
        this.stockQuantity = remainStock;
    }


    public void addStock(int quantity) {
        this.stockQuantity = this.stockQuantity + quantity;
    }
}
