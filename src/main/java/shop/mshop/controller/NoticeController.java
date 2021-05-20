package shop.mshop.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import shop.mshop.domain.Member;
import shop.mshop.message.response.NoticeReadResponse;
import shop.mshop.service.MemberService;
import shop.mshop.service.NoticeService;
import shop.mshop.util.HttpSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.invoke.MethodHandles;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final MemberService memberService;
    private final NoticeService noticeService;

    @GetMapping("/notice/list")
    public ModelAndView noticeList(HttpServletRequest request) {
        String page = "1";
        if (request.getParameter("page") != null) {
            page = request.getParameter("page");
        }

        ModelAndView mView = new ModelAndView();
        mView.setViewName("notice/listNotice");
        mView.addObject("nowPage", page);
        mView.addObject("sWord", request.getParameter("sword"));
        mView.addObject("sBy", request.getParameter("sby"));

        return mView;
    }

    @GetMapping("/notice/write")
    public ModelAndView noticeWrite(HttpSession httpSession) {
        ModelAndView mView = new ModelAndView();

        if(!HttpSessionUtils.isLoginUser(httpSession)){
            mView.setViewName("member/loginMember");
        }else{
            mView.setViewName("notice/writeNotice");
        }

        return mView;
    }

    @GetMapping("/notice/read/{id}")
    public ModelAndView noticeRead(@PathVariable("id") Long id, HttpSession httpSession) {
        ModelAndView mView = new ModelAndView();
        if (id == null) {
            mView.setViewName("redirect:list");
        }else{
            mView.setViewName("notice/readNotice");
            mView.addObject("nowId", id);
            //mView.addObject("isMyContent", HttpSessionUtils.isLoginUser(httpSession));
        }
        return mView;
    }

    @GetMapping("/notice/edit/{id}")
    public ModelAndView noticeEdit(@PathVariable("id") Long id, HttpSession httpSession) {
        ModelAndView mView = new ModelAndView();

        if (id == null) {
            mView.setViewName("redirect:list");
        }else{
            NoticeReadResponse response = noticeService.readById(id, httpSession);
            if (response.getWriterCheck()) {
                mView.setViewName("notice/editNotice");
                mView.addObject("nowId", id);
                mView.addObject("title", response.getTitle());
                mView.addObject("content", response.getContent().replace("<br>", "\n"));
            }else{
                mView.setViewName("redirect:list");
            }
        }
        return mView;
    }

}
