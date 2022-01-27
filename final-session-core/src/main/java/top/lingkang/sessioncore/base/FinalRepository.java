package top.lingkang.sessioncore.base;

import top.lingkang.sessioncore.config.FinalSessionProperties;
import top.lingkang.sessioncore.wrapper.FinalSession;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public interface FinalRepository {

    FinalSession getSession(String id);

    void setSession(String id,FinalSession session);

    void setFinalSessionProperties(FinalSessionProperties properties);

    void destroy();
}
