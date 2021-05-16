package shop.mshop.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginResponse {
    private Long id;
    private String memberId;
    private String name;
}
