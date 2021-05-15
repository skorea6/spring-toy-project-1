package shop.mshop.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mshop.domain.Address;
import shop.mshop.domain.Member;
import shop.mshop.message.StatusResponse;
import shop.mshop.service.MemberService;
import shop.mshop.util.HttpSessionUtils;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class ModelApiController {
    private final MemberService memberService;

    @GetMapping("/api/v1/model/index")
    public StatusResponse<ModelIndexResponse> modelIndexV1(HttpSession httpSession){
        Member member = memberService.findMemberBySession(HttpSessionUtils.getMemberFromSession(httpSession));
        ModelIndexResponse response = new ModelIndexResponse(member.getName());

        return new StatusResponse<>(response);
    }

    @GetMapping("/api/v1/model/updateMember")
    public StatusResponse<ModelUpdateMemberResponse> modelUpdateMemberV1(HttpSession httpSession){
        Member member = memberService.findMemberBySession(HttpSessionUtils.getMemberFromSession(httpSession));
        ModelUpdateMemberResponse response = new ModelUpdateMemberResponse(member.getMemberId(), member.getName(), member.getPhoneNumber(), member.getEmail(), member.getAddress());

        return new StatusResponse<>(response);
    }
    
    @Data
    @AllArgsConstructor
    static class ModelIndexResponse {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class ModelUpdateMemberResponse {
        private String memberId;
        private String name;
        private String phoneNumber;
        private String email;
        private Address address;
    }
}
