package shop.mshop.message.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import shop.mshop.message.CommentNoticeList;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommentNoticeListResponse extends ListResponseBase{
    private List<CommentNoticeList> commentNoticeList;
}
