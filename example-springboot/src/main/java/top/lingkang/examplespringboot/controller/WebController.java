package top.lingkang.examplespringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
@RestController
public class WebController {
    @GetMapping("")
    public Object index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getClass().getName());
        System.out.println(session.getAttribute("aa"));
        session.setAttribute("aa", System.currentTimeMillis());
        return "ok";
    }

    @GetMapping("index")
    public Object indexHtml() {
        return new ModelAndView("index");
    }

}
