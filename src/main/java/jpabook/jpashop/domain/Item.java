package jpabook.jpashop.domain;

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

    public List<Category> getCategories() {
        return categories;
    }
}
