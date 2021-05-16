package shop.mshop.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import shop.mshop.util.HttpSessionUtils;

import javax.servlet.http.HttpSession;
import java.lang.invoke.MethodHandles;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping("/notice/list")
    public ModelAndView noticeList(@Param("page") Integer page) {
        if (page == null) {
            page = 1;
        }
        ModelAndView mView = new ModelAndView();
        mView.setViewName("notice/listNotice");
        mView.addObject("nowPage", page);

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

    @GetMapping("/notice/read")
    public ModelAndView noticeRead(@Param("id") Integer id) {
        ModelAndView mView = new ModelAndView();
        if (id == null) {
            mView.setViewName("redirect:list");
        }else{
            mView.setViewName("notice/readNotice");
            mView.addObject("nowId", id);
        }
        return mView;
    }

}
