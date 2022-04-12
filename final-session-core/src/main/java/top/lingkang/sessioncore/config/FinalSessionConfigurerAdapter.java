package top.lingkang.sessioncore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lingkang.sessioncore.base.FinalRepository;
import top.lingkang.sessioncore.base.impl.FinalDataBaseRepository;
import top.lingkang.sessioncore.base.impl.FinalMemoryRepository;
import top.lingkang.sessioncore.error.FinalValidException;
import top.lingkang.sessioncore.wrapper.FinalServletRequestWrapper;
import top.lingkang.sessioncore.wrapper.FinalSession;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class FinalSessionConfigurerAdapter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(FinalDataBaseRepository.class);
    private FinalSessionProperties properties = new FinalSessionProperties();
    // 会话存储仓库
    private FinalRepository repository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FinalSession session = null;
        long current = System.currentTimeMillis();

        // 获取sessionId
        String sessionId = properties.getSessionId().getSessionId(request, properties);

        if (sessionId != null) {
            session = repository.getSession(sessionId);
        }
        if (session == null) { // 生成会话
            session = properties.getGenerateSession().generateSession(request, properties.getIdGenerate());
        } else {
            // 判断预留时间
            if (!properties.isUpdateAccessTime()) {
                if (session.getLastAccessedTime() + properties.getMaxValidTime() - properties.getReserveTime() < current) {
                    // 说明之前的会话已经到期， 重新生成会话
                    session = properties.getGenerateSession().generateSession(request, properties.getIdGenerate());
                }
            } else {
                // 判断令牌是否有效
                if (session.getLastAccessedTime() + properties.getMaxValidTime() < current) {
                    // 说明之前的会话已经到期， 生成会话
                    session = properties.getGenerateSession().generateSession(request, properties.getIdGenerate());
                } else {// 更新访问时间
                    session.updateAccessTime(current);
                }
            }
        }

        FinalServletRequestWrapper wrapper = new FinalServletRequestWrapper(request);
        wrapper.setSession(session);

        // 对响应添加 cookie或自定义操作
        properties.getSessionId().setSessionId((HttpServletResponse) servletResponse, properties, session.getId());

        // 放行
        filterChain.doFilter(wrapper, servletResponse);

        // 是否更新会话最后访问时间
        if (session.isExistsUpdate() || properties.isUpdateAccessTime()) {
            session.setExistsUpdate(false);
            repository.setSession(session.getId(), session);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        configurer(properties);

        if (!properties.isUpdateAccessTime()) {
            if (properties.getMaxValidTime() < properties.getReserveTime()) {
                log.error("最大令牌时效时间不能比预留时间小， maxValidTime < reserveTime error !",
                        new FinalValidException("最大令牌时效时间不能比预留时间小， maxValidTime < reserveTime error !"));
                System.exit(0);
            }
        }

        repository = properties.getRepository();
        if (repository == null) {
            repository = new FinalMemoryRepository();
            log.warn("final-session use memory repository! final-session 使用内存作为存储，不适用于分布式会话！");
        }
        repository.setFinalSessionProperties(properties);
        log.info("final-session init finish...");
    }

    @Override
    public void destroy() {
        repository.destroy();
    }

    protected void configurer(FinalSessionProperties properties) {
        this.properties = properties;
    }
}
