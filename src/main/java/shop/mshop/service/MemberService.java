package shop.mshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mshop.constant.CommonConstant;
import shop.mshop.domain.Member;
import shop.mshop.exception.global.ApiException;
import shop.mshop.message.request.MemberDeleteRequest;
import shop.mshop.message.request.MemberUpdateRequest;
import shop.mshop.message.request.MemberUpdatePwRequest;
import shop.mshop.repository.MemberRepository;
import shop.mshop.util.HttpSessionUtils;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Long save(Member member) {
        // 이메일 유효성 검사
        validateRealEmail(member.getEmail());

        // 이름 유효성 확인
        validateRealName(member.getName());

        // 휴대폰 유효성 확인
        validateRealPhoneNumber(member.getPhoneNumber());

        // 중복 조회
        validateDuplicationMemberId(member.getMemberId());

        // 중복 email 조회
        validateDuplicationEmail(member.getEmail());

        // [개발예정]
        // 중복 nick 조회

        // 아이디는 4글자 이상 12글자 이하로
        validateLengthMemberId(member.getMemberId());

        // 비번은 6글자 이상 18글자 이하로
        validateLengthPassword(member.getPassword());


        // [개발예정]
        // 닉네임은 한글,영어,숫자로만
        // 닉네임은 한글 6자 이내, 영어 12자 이내

        // 비밀번호는 영어와 숫자로 이루어지게 설정
        validateRulePassword(member.getPassword());


        // 이름은 2글자 이상 5글자 이하
        validateLengthName(member.getName());

        // 비밀번호 암호화
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // DB
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Long update(MemberUpdateRequest request, String sessionKey) {
        // 위의 멤버는 현재 세션의 멤버를 가져온것이에요.
        Member member = this.findMemberBySession(sessionKey);

        // 이메일 유효성 검사
        validateRealEmail(request.getEmail());

        // 이름 유효성 확인
        validateRealName(request.getName());

        // 휴대폰 유효성 확인
        validateRealPhoneNumber(request.getPhoneNumber());

        // 중복 email 조회 (내 이메일일 경우 조회 제외)
        if(!member.getEmail().equals(request.getEmail())){
            validateDuplicationEmail(request.getEmail());
        }

        // 이름은 2글자 이상 5글자 이하
        validateLengthName(request.getName());

        // DB
        member.updateMember(request.getName(), request.getEmail(), request.getPhoneNumber(), request.getAddress());

        return member.getId();
    }

    @Transactional
    public Long updatePw(MemberUpdatePwRequest request, String sessionKey) {
        // 위의 멤버는 현재 세션의 멤버를 가져온것이에요.
        Member member = this.findMemberBySession(sessionKey);

        // 현재 비밀번호와 맞는지 확인
        if (!passwordEncoder.matches(request.getOldPassword(), member.getPassword())) {
            throw new ApiException(CommonConstant.ERR_NOT_SEARCH_MATCHED_KEY, "비밀번호가 일치하지 않습니다.", null);
        }

        // 새로운 비밀번호와 재입력한 비밀번호 일치 확인
        if (!request.getNewPassword1().equals(request.getNewPassword2())) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "새로운 비밀번호와 재입력한 비밀번호가 같지 않습니다.", null);
        }

        // 기존 비밀번호와 동일한지 확인
        if (request.getOldPassword().equals(request.getNewPassword1())) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "기존 비밀번호와 동일할 수 없습니다.", null);
        }

        // 비번은 6글자 이상 18글자 이하로
        validateLengthPassword(request.getNewPassword1());

        // 비밀번호는 영어와 숫자로 이루어지게 설정
        validateRulePassword(request.getNewPassword1());

        // DB
        member.setPassword(passwordEncoder.encode(request.getNewPassword1()));

        return member.getId();
    }

    @Transactional
    public Long delete(MemberDeleteRequest request, String sessionKey) {
        Member member = this.findMemberBySession(sessionKey);
        Long id = member.getId();

        // 현재 비밀번호와 맞는지 확인
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new ApiException(CommonConstant.ERR_NOT_SEARCH_MATCHED_KEY, "비밀번호가 일치하지 않습니다.", null);
        }

        // DB
        memberRepository.delete(member);

        return id;
    }

    private void validateLengthName(String name) {
        if (name.length() < 2 || name.length() > 5) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "이름 글자수를 확인하세요.", null);
        }
    }

    private void validateRulePassword(String password) {
        if(!Pattern.matches("^[0-9a-zA-Z]*$", password)){
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "비밀번호에 영어와 숫자를 포함하여 입력하세요.", null);
        }
    }

    private void validateLengthPassword(String password) {
        if(password.length() < 6 || password.length() > 18){
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "비밀번호는 6글자 이상 18글자 이하로 입력하세요.", null);
        }
    }

    private void validateLengthMemberId(String memberId) {
        if(memberId.length() < 4 || memberId.length() > 12){
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "아이디는 4글자 이상 12글자 이하로 입력하세요.", null);
        }
    }

    private void validateRealPhoneNumber(String phoneNumber) {
        if (!Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phoneNumber)) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "전화번호가 유효하지 않습니다.", null);
        }
    }

    private void validateRealName(String name) {
        if (!Pattern.matches("^[가-힣]*$", name)) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "이름이 유효하지 않습니다.", null);
        }
    }

    private void validateRealEmail(String email) {
        if(!Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", email)){
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "이메일이 유효하지 않습니다.", null);
        }
    }

    private void validateDuplicationMemberId(String memberId) {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);
        if (!findMember.isEmpty()) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "이미 존재하는 아이디입니다", null);
            //throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    private void validateDuplicationEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (!findMember.isEmpty()) {
            throw new ApiException(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "이미 존재하는 이메일입니다", null);
        }
    }






    public Member loginByMemberId(String memberId, String password, HttpSession httpSession) {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);

        if (findMember.isEmpty()) {
            throw new ApiException(CommonConstant.ERR_NOT_SEARCH_MATCHED_KEY, "해당 회원이 존재하지 않습니다.", null);
        }

        if (!passwordEncoder.matches(password, findMember.get().getPassword())) {
            throw new ApiException(CommonConstant.ERR_NOT_SEARCH_MATCHED_KEY, "비밀번호가 일치하지 않습니다.", null);
        }

        // 세션 저장
        HttpSessionUtils.setLoginSession(httpSession, findMember.get().getSessionKey());

        return findMember.get();
    }

    public Member findMemberBySession(String sessionKey) {
        Optional<Member> findMember = memberRepository.findBySessionKey(sessionKey);

        return findMember.get();
    }





}
