package top.lingkang.sessioncore.base;

import top.lingkang.sessioncore.config.FinalSessionProperties;
import top.lingkang.sessioncore.wrapper.FinalSession;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * Created by 2022/1/
 * 会话存储接口，默认实现类FinalMemoryRepository
 * 可通过实现改类达到存储会话到数据库、redis、或其他存储方式
 */
public interface FinalRepository {

    FinalSession getSession(String id);

    void setSession(String id,FinalSession session);

    void deleteSession(String id, HttpServletRequest request);

    void setFinalSessionProperties(FinalSessionProperties properties);

    void destroy();
}
