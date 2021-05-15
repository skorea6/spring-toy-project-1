package shop.mshop.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.mshop.constant.ApiExceptionConstant;
import shop.mshop.domain.Member;
import shop.mshop.message.StatusResponse;
import shop.mshop.message.request.*;
import shop.mshop.message.response.*;
import shop.mshop.service.MemberService;
import shop.mshop.util.HttpSessionUtils;
import shop.mshop.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    ApiExceptionConstant apiExceptionConstant = new ApiExceptionConstant();

    @PutMapping("/api/v1/signupMember")
    public StatusResponse<CreateMemberResponse> signupMemberV1(@RequestBody CreateMemberRequest request) {
        // 오류 Exception 처리
        apiExceptionConstant.checkRequireAttr(request.getMemberId(), "memberId");
        apiExceptionConstant.checkRequireAttr(request.getPassword(), "password");
        apiExceptionConstant.checkRequireAttr(request.getName(), "name");
        apiExceptionConstant.checkRequireAttr(request.getEmail(), "email");
        apiExceptionConstant.checkRequireAttr(request.getPhoneNumber(), "phoneNumber");
        apiExceptionConstant.checkRequireAttr(request.getAddress().getCity(), "address.city");
        apiExceptionConstant.checkRequireAttr(request.getAddress().getStreet(), "address.street");
        apiExceptionConstant.checkRequireAttr(request.getAddress().getZipcode(), "address.zipcode");

        // 클라이언트 아이피 주소 가져오기
        String ipAddress = null;
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ipAddress = IpAddressUtil.getIp(httpServletRequest);

        Member member = new Member(request.getMemberId(), request.getPassword(), request.getName(), request.getAddress(), request.getEmail(), request.getPhoneNumber());
        member.setIpAddress(ipAddress);
        Long savedMemberId = memberService.save(member);

        return new StatusResponse<>(new CreateMemberResponse(savedMemberId));
    }


    @PostMapping("/api/v1/loginMember")
    public StatusResponse<LoginMemberResponse> loginMemberV1(@RequestBody LoginMemberRequest request, HttpSession httpSession) {
        apiExceptionConstant.checkRequireAttr(request.getMemberId(), "memberId");
        apiExceptionConstant.checkRequireAttr(request.getPassword(), "password");

        // 멤버 찾기
        Member member = memberService.loginByMemberId(request.getMemberId(), request.getPassword(), httpSession);

        return new StatusResponse<>(new LoginMemberResponse(member.getId(), member.getMemberId(), member.getName()));
    }


    @PostMapping("/api/v1/updateMember")
    public StatusResponse<UpdateMemberResponse> updateMemberV1(@RequestBody UpdateMemberRequest request, HttpSession httpSession) {
        // 빈값 체크 Exception
        apiExceptionConstant.checkRequireAttr(request.getName(), "name");
        apiExceptionConstant.checkRequireAttr(request.getEmail(), "email");
        apiExceptionConstant.checkRequireAttr(request.getPhoneNumber(), "phoneNumber");
        apiExceptionConstant.checkRequireAttr(request.getAddress().getCity(), "address.city");
        apiExceptionConstant.checkRequireAttr(request.getAddress().getStreet(), "address.street");
        apiExceptionConstant.checkRequireAttr(request.getAddress().getZipcode(), "address.zipcode");

        // 멤버 찾기
        Long updatedMemberId = memberService.update(request, HttpSessionUtils.getMemberFromSession(httpSession));

        return new StatusResponse<>(new UpdateMemberResponse(updatedMemberId));
    }

    @PostMapping("/api/v1/updatePwMember")
    public StatusResponse<UpdatePwMemberResponse> updateMemberV1(@RequestBody UpdatePwMemberRequest request, HttpSession httpSession) {
        // 빈값 체크 Exception
        apiExceptionConstant.checkRequireAttr(request.getOldPassword(), "oldPassword");
        apiExceptionConstant.checkRequireAttr(request.getNewPassword1(), "newPassword1");
        apiExceptionConstant.checkRequireAttr(request.getNewPassword2(), "newPassword2");

        // 멤버 찾기
        Long updatedMemberId = memberService.updatePw(request, HttpSessionUtils.getMemberFromSession(httpSession));

        // 세션 풀어버리기
        HttpSessionUtils.setLogoutSession(httpSession);

        return new StatusResponse<>(new UpdatePwMemberResponse(updatedMemberId));
    }

    @PostMapping("/api/v1/deleteMember")
    public StatusResponse<DeleteMemberResponse> updateMemberV1(@RequestBody DeleteMemberRequest request, HttpSession httpSession) {
        // 빈값 체크 Exception
        apiExceptionConstant.checkRequireAttr(request.getPassword(), "password");

        // 멤버 찾기
        Member deletedMember = memberService.delete(request, HttpSessionUtils.getMemberFromSession(httpSession));

        // 세션 풀어버리기
        HttpSessionUtils.setLogoutSession(httpSession);

        return new StatusResponse<>(new DeleteMemberResponse(deletedMember.getId(), deletedMember.getName()));
    }


//    @Data
//    static class ResultResponse<T> {
//        private int statusCode;
//        private String statusMessage;
//        private String responseTime;
//        private T data;
//
//        public ResultResponse() {
//            this.statusCode = 200;
//            this.statusMessage = "success";
//            this.responseTime = DateUtil.getCurrentTime();
//            this.data = null;
//        }
//
//        public ResultResponse(T data) {
//            this.statusCode = 200;
//            this.statusMessage = "success";
//            this.responseTime = DateUtil.getCurrentTime();
//            this.data = data;
//        }
//    }

}
