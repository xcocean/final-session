package top.lingkang.sessioncore.base;

import top.lingkang.sessioncore.config.FinalSessionProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取和设置会话id类，默认实现类 FinalSessionIdCookie，
 * 可以通过实现该类来定义会话id从cookie、请求头、或者参数中读取
 */
public interface FinalSessionId {
    String getSessionId(HttpServletRequest request, FinalSessionProperties properties);

    void setSessionId(HttpServletResponse response, FinalSessionProperties properties, String sessionId);
}
