package shop.mshop.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import shop.mshop.message.NoticeList;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class NoticeListResponse extends ListResponseBase{
    private List<NoticeList> noticeList;
}
