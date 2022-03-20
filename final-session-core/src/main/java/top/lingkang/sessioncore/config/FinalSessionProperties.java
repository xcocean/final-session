package top.lingkang.sessioncore.config;

import top.lingkang.sessioncore.base.FinalRepository;
import top.lingkang.sessioncore.base.FinalSessionId;
import top.lingkang.sessioncore.base.IdGenerate;
import top.lingkang.sessioncore.base.impl.FinalIdGenerate;
import top.lingkang.sessioncore.base.impl.FinalSessionIdCookie;
import top.lingkang.sessioncore.wrapper.DefaultGenerateSession;
import top.lingkang.sessioncore.wrapper.FinalGenerateSession;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class FinalSessionProperties {
    // cookie 名称
    private String cookieName = "fs";
    // cookie的age，若为true则写入失效时间
    private boolean cookieAge;
    // cookie存在最大时间 1小时
    private long maxValidTime = 3600000L;
    // 生成id
    private IdGenerate idGenerate = new FinalIdGenerate();
    // 每次访问是否更新最后访问时间，即续时功能。
    private boolean isUpdateAccessTime;
    // 保留时间，即每次执行访问操作后台，预留5分钟，防止会话失效的极限时间，在accessUpdateTime==false时启用
    private long reserveTime = 300000L;

    private FinalRepository repository;

    private FinalGenerateSession generateSession = new DefaultGenerateSession();

    private FinalSessionId sessionId = new FinalSessionIdCookie();

    public FinalSessionId getSessionId() {
        return sessionId;
    }

    public void setSessionId(FinalSessionId sessionId) {
        this.sessionId = sessionId;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public long getMaxValidTime() {
        return maxValidTime;
    }

    public void setMaxValidTime(long maxValidTime) {
        this.maxValidTime = maxValidTime;
    }

    public IdGenerate getIdGenerate() {
        return idGenerate;
    }

    public void setIdGenerate(IdGenerate idGenerate) {
        this.idGenerate = idGenerate;
    }

    public boolean isUpdateAccessTime() {
        return isUpdateAccessTime;
    }

    public void setUpdateAccessTime(boolean updateAccessTime) {
        isUpdateAccessTime = updateAccessTime;
    }

    public long getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(long reserveTime) {
        this.reserveTime = reserveTime;
    }

    public boolean isCookieAge() {
        return cookieAge;
    }

    public void setCookieAge(boolean cookieAge) {
        this.cookieAge = cookieAge;
    }

    public FinalRepository getRepository() {
        return repository;
    }

    public void setRepository(FinalRepository repository) {
        this.repository = repository;
    }

    public FinalGenerateSession getGenerateSession() {
        return generateSession;
    }

    public void setGenerateSession(FinalGenerateSession generateSession) {
        this.generateSession = generateSession;
    }
}
