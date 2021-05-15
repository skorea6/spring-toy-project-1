package shop.mshop.message.request;

import lombok.Data;

@Data
public class UpdatePwMemberRequest {
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
}
