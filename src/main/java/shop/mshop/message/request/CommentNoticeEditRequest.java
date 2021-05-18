package shop.mshop.message.request;

import lombok.Data;

@Data
public class CommentNoticeEditRequest {
    private Long commentId;
    private String comment;
}
