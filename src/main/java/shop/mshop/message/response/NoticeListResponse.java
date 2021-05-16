package shop.mshop.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.mshop.message.NoticeList;

import java.util.List;

@Data
public class NoticeListResponse extends ListResponseBase{
    private List<NoticeList> noticeList;
}
