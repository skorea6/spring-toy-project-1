package shop.mshop.message.request;

import lombok.Data;

@Data
public class NoticeEditRequest {
    private Long id;
    private String title;
    private String content;
}
