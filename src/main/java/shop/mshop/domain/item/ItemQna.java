package shop.mshop.domain.item;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class ItemQna {
    @Id
    @GeneratedValue
    @Column(name = "item_qna_id")
    private Long id;

    private String type;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
