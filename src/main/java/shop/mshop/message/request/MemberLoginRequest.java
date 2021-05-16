package shop.mshop.message.request;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private String memberId;
    private String password;
}
