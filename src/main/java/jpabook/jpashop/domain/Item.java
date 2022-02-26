package jpabook.jpashop.domain;

import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Item extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "item_id", nullable = false)
    private Long id;
    @Column
    private String name;
    @Column
    private int stockQuantity;
    @Column
    private int price;
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new java.util.ArrayList<>();

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
