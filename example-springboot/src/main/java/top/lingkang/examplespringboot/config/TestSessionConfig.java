package top.lingkang.examplespringboot.config;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.lingkang.sessioncore.base.FinalSessionId;
import top.lingkang.sessioncore.config.FinalSessionConfigurerAdapter;
import top.lingkang.sessioncore.config.FinalSessionProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/6/17
 */
@Order(Integer.MIN_VALUE - 1995)
@Component
public class TestSessionConfig extends FinalSessionConfigurerAdapter {
    @Override
    protected void configurer(FinalSessionProperties properties) {
        properties.setSessionId(new FinalSessionId() {
            @Override
            public String getSessionId(HttpServletRequest request, FinalSessionProperties properties) {
                // 获取会话id的方式，可以通过 请求头、请求参数、cookie中获取
                return request.getHeader("token");// 这只是一个demo
            }

            @Override
            public void setSessionId(HttpServletRequest request, HttpServletResponse response, FinalSessionProperties properties, String sessionId) {
                // 前后端分离，不设置会话ID到session
            }
        });
    }
}
