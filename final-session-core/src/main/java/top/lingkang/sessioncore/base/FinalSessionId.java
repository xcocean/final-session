package top.lingkang.sessioncore.base;

import top.lingkang.sessioncore.config.FinalSessionProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FinalSessionId {
    String getSessionId(HttpServletRequest request, FinalSessionProperties properties);

    void setSessionId(HttpServletResponse response, FinalSessionProperties properties, String sessionId);
}
