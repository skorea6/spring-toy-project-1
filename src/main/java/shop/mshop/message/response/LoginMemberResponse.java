package shop.mshop.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMemberResponse {
    private Long id;
    private String memberId;
    private String name;
}
