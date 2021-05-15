package shop.mshop.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import shop.mshop.util.HttpSessionUtils;

import javax.servlet.http.HttpSession;
import java.lang.invoke.MethodHandles;

@Controller
@RequiredArgsConstructor
public class MemberController {
    final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping("/")
    public ModelAndView home(HttpSession httpSession) {
        ModelAndView mView = new ModelAndView();
        mView.setViewName("index");
        mView.addObject("isLoginUser", HttpSessionUtils.isLoginUser(httpSession));

        return mView;
    }

    @GetMapping("/member/signup")
    public ModelAndView createForm(HttpSession httpSession) {
        ModelAndView mView = new ModelAndView();

        if(!HttpSessionUtils.isLoginUser(httpSession)){
            mView.setViewName("member/signupMember");
        }else{
            mView.setViewName("index");
        }

        return mView;
    }

    @GetMapping("/member/login")
    public ModelAndView loginForm(HttpSession httpSession) {
        ModelAndView mView = new ModelAndView();

        if(!HttpSessionUtils.isLoginUser(httpSession)){
            mView.setViewName("member/loginMember");
        }else{
            mView.setViewName("index");
        }

        return mView;
    }


    // 무조건 로그인 안돼있으면 튕기기
    @GetMapping("/member/update")
    public ModelAndView updateForm(HttpSession httpSession) {
        ModelAndView mView = new ModelAndView();

        if(!HttpSessionUtils.isLoginUser(httpSession)){
            mView.setViewName("index");
        }else{
            mView.setViewName("member/updateMember");
        }

        return mView;
    }

    // 무조건 로그인 안돼있으면 튕기기
    @GetMapping("/member/updatePw")
    public ModelAndView updatePwForm(HttpSession httpSession) {
        ModelAndView mView = new ModelAndView();

        if(!HttpSessionUtils.isLoginUser(httpSession)){
            mView.setViewName("index");
        }else{
            mView.setViewName("member/updatePwMember");
        }

        return mView;
    }

    // 무조건 로그인 안돼있으면 튕기기
    @GetMapping("/member/delete")
    public ModelAndView deleteForm(HttpSession httpSession) {
        ModelAndView mView = new ModelAndView();

        if(!HttpSessionUtils.isLoginUser(httpSession)){
            mView.setViewName("index");
        }else{
            mView.setViewName("member/deleteMember");
        }

        return mView;
    }

    @GetMapping("/member/logout")
    public String logoutForm(HttpSession httpSession) {
        HttpSessionUtils.setLogoutSession(httpSession);

        return "redirect:/";
    }
}
