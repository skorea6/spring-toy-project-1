package shop.mshop.message.request;

import lombok.Data;

@Data
public class CommentNoticeWriteRequest {
    private Long id;
    private String comment;
}
