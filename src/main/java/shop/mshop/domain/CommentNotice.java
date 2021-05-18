package shop.mshop.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CommentNotice extends BaseDomain{
    @Id
    @GeneratedValue
    @Column(name = "comment_notice_id")
    private Long id;

    // 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @Column(columnDefinition = "LONGTEXT")
    private String comment;

    public CommentNotice(Member member, Notice notice, String comment) {
        this.member = member;
        this.notice = notice;
        this.comment = comment;
    }

    protected CommentNotice() {
    }
}
