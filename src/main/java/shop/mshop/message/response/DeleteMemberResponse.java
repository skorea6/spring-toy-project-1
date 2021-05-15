package shop.mshop.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteMemberResponse {
    private Long id;
    private String name;
}
