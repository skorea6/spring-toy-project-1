package shop.mshop.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Orders {
    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    private LocalDateTime orderDate;
    private OrdersStatus status;

    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "orders")
    private List<Delivery> deliveries = new ArrayList<>();

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems = new ArrayList<>();
}
