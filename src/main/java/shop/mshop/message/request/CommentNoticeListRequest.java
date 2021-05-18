package shop.mshop.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommentNoticeListRequest extends ListRequestBase{
    private Long noticeId;
}
