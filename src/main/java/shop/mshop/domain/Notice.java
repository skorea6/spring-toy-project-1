package shop.mshop.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Notice extends BaseDomain {
    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    // 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;


    public Notice(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

    protected Notice() {

    }
}
