package top.lingkang.sessioncore.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/3/9
 * 自定义异常处理，他们通常是redis或者数据库读取的异常
 */
public interface FinalSessionExceptionHandler {
    void handler(Exception e, HttpServletRequest request, HttpServletResponse response);
}
