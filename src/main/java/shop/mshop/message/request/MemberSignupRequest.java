package shop.mshop.message.request;

import lombok.Data;
import shop.mshop.domain.Address;

@Data
public class MemberSignupRequest {
    private String memberId;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private Address address;
}
