package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter @Setter
public class Category extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "category_id", nullable = false)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new java.util.ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new java.util.ArrayList<>();

    public void addChild(Category category){
        this.child.add(category);
        category.setParent(this);
    }

    public void addItems(Item item){
        this.items.add(item);
        item.getCategories().add(this);
    }

}
