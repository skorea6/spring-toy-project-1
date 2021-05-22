package shop.mshop.domain.item;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class ItemReview {
    @Id
    @GeneratedValue
    @Column(name = "item_review_id")
    private Long id;

    private Integer star;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private LocalDateTime dateTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
