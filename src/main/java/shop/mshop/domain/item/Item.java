package shop.mshop.domain.item;

import lombok.Getter;
import shop.mshop.domain.CategoryItem;
import shop.mshop.domain.OrderItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Integer price;

    @Column(columnDefinition = "LONGTEXT")
    private String information;

    private Integer stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<ItemReview> itemReviews = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<ItemQna> itemQnas = new ArrayList<>();
}
