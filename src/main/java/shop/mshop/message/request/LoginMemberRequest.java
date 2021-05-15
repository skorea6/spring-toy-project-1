package shop.mshop.message.request;

import lombok.Data;

@Data
public class LoginMemberRequest {
    private String memberId;
    private String password;
}
