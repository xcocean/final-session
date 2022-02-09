package top.lingkang.sessioncore.base.impl;

import top.lingkang.sessioncore.base.FinalSessionId;
import top.lingkang.sessioncore.config.FinalSessionProperties;
import top.lingkang.sessioncore.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/2/9
 */
public class FinalSessionIdCookie implements FinalSessionId {
    @Override
    public String getSessionId(HttpServletRequest request, FinalSessionProperties properties) {
        return CookieUtils.getCookieValue(properties.getCookieName(), request.getCookies());
    }

    @Override
    public void setSessionId(HttpServletResponse response, FinalSessionProperties properties, String sessionId) {
        CookieUtils.addSessionIdToCookie(
                properties.getCookieName(),
                sessionId,
                properties.isCookieAge(),
                properties.getMaxValidTime(),
                response
        );
    }
}
