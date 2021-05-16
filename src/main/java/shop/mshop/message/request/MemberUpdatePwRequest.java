package shop.mshop.message.request;

import lombok.Data;

@Data
public class MemberUpdatePwRequest {
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
}
