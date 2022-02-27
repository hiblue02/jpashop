package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Setter
    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @Setter(value = AccessLevel.PROTECTED)
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void addChild(Category category){
        this.child.add(category);
        category.setParent(this);
    }

    public void addItems(Item item){
        this.items.add(item);
        item.getCategories().add(this);
    }

}
