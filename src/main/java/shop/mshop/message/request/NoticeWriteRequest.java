package shop.mshop.message.request;

import lombok.Data;

@Data
public class NoticeWriteRequest {
    private String title;
    private String content;
}
