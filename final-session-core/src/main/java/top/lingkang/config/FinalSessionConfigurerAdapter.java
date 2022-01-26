package top.lingkang.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lingkang.base.FinalRepository;
import top.lingkang.base.impl.FinalMemoryRepository;
import top.lingkang.utils.CookieUtils;
import top.lingkang.wrapper.FinalServletRequestWrapper;
import top.lingkang.wrapper.FinalSession;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class FinalSessionConfigurerAdapter implements Filter {
    private final Logger log = LoggerFactory.getLogger(FinalSessionConfigurerAdapter.class);
    private FinalSessionProperties properties = new FinalSessionProperties();
    // 会话存储仓库
    private FinalRepository repository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FinalSession session = null;
        long current = System.currentTimeMillis();
        boolean isCookie = false;

        // 获取cookie
        String sessionId = CookieUtils.getCookieValue(properties.getCookieName(), request.getCookies());
        if (sessionId != null) {
            session = repository.getSession(sessionId);
        }
        if (session == null) { // 生成会话
            session = new FinalSession(servletRequest.getServletContext(), properties.getIdGenerate().generateId());
            isCookie = true;
        } else {
            // 判断预留时间
            if (!properties.isAccessUpdateTime()) {
                if (session.getLastAccessedTime() + properties.getMaxValidTime() - properties.getReserveTime() < current) {
                    // 说明之前的会话已经到期
                    session = new FinalSession(servletRequest.getServletContext(), properties.getIdGenerate().generateId());
                    isCookie = true;
                }
            } else {
                // 判断令牌是否有效
                if (session.getLastAccessedTime() + properties.getMaxValidTime() < current) {
                    // 说明之前的会话已经到期
                    session = new FinalSession(servletRequest.getServletContext(), properties.getIdGenerate().generateId());
                    isCookie = true;
                } else {// 更新访问时间
                    session.updateAccessTime(current);
                }
            }
        }

        if (isCookie) {
            CookieUtils.addSessionIdToCookie(
                    properties.getCookieName(),
                    session.getId(),
                    properties.isCookieAge(),
                    properties.getMaxValidTime(),
                    (HttpServletResponse) servletResponse
            );

            session.setExistsUpdate(true);
        }

        FinalServletRequestWrapper wrapper = new FinalServletRequestWrapper(request);
        wrapper.setSession(session);

        // 放行
        filterChain.doFilter(wrapper, servletResponse);

        // 是否更新会话最后访问时间
        if (session.isExistsUpdate() || properties.isAccessUpdateTime()) {
            session.updateAccessTime();
            repository.setSession(session.getId(), session);
        }


    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        configurer(properties);

        if (!properties.isAccessUpdateTime()) {
            if (properties.getMaxValidTime() < properties.getReserveTime()) {
                log.error("最大令牌时效时间不能比预留时间小， maxValidTime < reserveTime error !",
                        new RuntimeException("最大令牌时效时间不能比预留时间小， maxValidTime < reserveTime error !"));
                System.exit(0);
            }
        }

        repository = properties.getRepository();
        if (repository == null) {
            repository = new FinalMemoryRepository();
            log.warn("final-session use memory repository! final-session 使用内存作为存储，不适用与分布式会话！");
        }
        repository.setFinalSessionProperties(properties);
    }

    @Override
    public void destroy() {
        repository.destroy();
    }

    protected void configurer(FinalSessionProperties properties) {
        this.properties = properties;
    }

}
