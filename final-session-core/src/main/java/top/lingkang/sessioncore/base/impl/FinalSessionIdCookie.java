package top.lingkang.sessioncore.base.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lingkang.sessioncore.base.FinalSessionId;
import top.lingkang.sessioncore.config.FinalSessionProperties;
import top.lingkang.sessioncore.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/2/9
 */
public class FinalSessionIdCookie implements FinalSessionId {
    private static final Logger log= LoggerFactory.getLogger(FinalSessionIdCookie.class);
    @Override
    public String getSessionId(HttpServletRequest request, FinalSessionProperties properties) {
        return CookieUtils.getCookieValue(properties.getCookieName(), request.getCookies());
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, FinalSessionProperties properties, String sessionId) {
        try {
            CookieUtils.addSessionIdToCookie(
                    properties.getCookieName(),
                    sessionId,
                    properties.isCookieAge(),
                    properties.getMaxValidTime(),
                    response
            );
        } catch (Exception e) {
            log.error("add cookie error!",e);
        }
    }
}
