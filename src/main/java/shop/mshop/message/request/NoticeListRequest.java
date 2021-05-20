package shop.mshop.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class NoticeListRequest extends ListRequestBase{
    private String searchWord;
    private String searchBy;
}
