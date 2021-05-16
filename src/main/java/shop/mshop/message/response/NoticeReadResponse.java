package shop.mshop.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NoticeReadResponse {
    private Long id;
    private String title;
    private String content;
    private String writerMemberId;
    private String writerName;
    private String createdDate;
}
