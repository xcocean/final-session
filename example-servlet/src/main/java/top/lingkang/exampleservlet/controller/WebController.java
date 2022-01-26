package top.lingkang.exampleservlet.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
@WebServlet("/")
public class WebController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println(session.getClass().getName());
        System.out.println(session.getAttribute("a"));
        session.setAttribute("a",System.currentTimeMillis());
    }
}
