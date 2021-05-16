package shop.mshop.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoticeList {
    private Long noticeId;
    private String title;
    private String writerName;
    private String writerMemberId;
    private String createdDate;
}
