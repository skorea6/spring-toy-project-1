package shop.mshop.message.request;

import lombok.Data;
import shop.mshop.domain.Address;

@Data
public class UpdateMemberRequest {
    private String name;
    private String phoneNumber;
    private String email;
    private Address address;
}
