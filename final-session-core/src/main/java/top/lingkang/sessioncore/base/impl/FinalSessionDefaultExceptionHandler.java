package top.lingkang.sessioncore.base.impl;

import top.lingkang.sessioncore.base.FinalSessionExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * Created by 2022/3/9
 */
public class FinalSessionDefaultExceptionHandler implements FinalSessionExceptionHandler {
    @Override
    public void handler(Exception e, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().println("final-session在处理会话时出现异常，final-session encountered an exception while processing the session");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
