package shop.mshop.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentNoticeList {
    private Long id;
    private String comment;
    private String writerName;
    private String writerMemberId;
    private String createdDate;
}
