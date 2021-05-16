package shop.mshop.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    // 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String content;
}
