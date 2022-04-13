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

    public FinalSessionProperties setSessionId(FinalSessionId sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getCookieName() {
        return cookieName;
    }

    public FinalSessionProperties setCookieName(String cookieName) {
        this.cookieName = cookieName;
        return this;
    }

    public long getMaxValidTime() {
        return maxValidTime;
    }

    public FinalSessionProperties setMaxValidTime(long maxValidTime) {
        this.maxValidTime = maxValidTime;
        return this;
    }

    public IdGenerate getIdGenerate() {
        return idGenerate;
    }

    public FinalSessionProperties setIdGenerate(IdGenerate idGenerate) {
        this.idGenerate = idGenerate;
        return this;
    }

    public boolean isUpdateAccessTime() {
        return isUpdateAccessTime;
    }

    public FinalSessionProperties setUpdateAccessTime(boolean updateAccessTime) {
        isUpdateAccessTime = updateAccessTime;
        return this;
    }

    public long getReserveTime() {
        return reserveTime;
    }

    public FinalSessionProperties setReserveTime(long reserveTime) {
        this.reserveTime = reserveTime;
        return this;
    }

    public boolean isCookieAge() {
        return cookieAge;
    }

    public FinalSessionProperties setCookieAge(boolean cookieAge) {
        this.cookieAge = cookieAge;
        return this;
    }

    public FinalRepository getRepository() {
        return repository;
    }

    public FinalSessionProperties setRepository(FinalRepository repository) {
        this.repository = repository;
        return this;
    }

    public FinalGenerateSession getGenerateSession() {
        return generateSession;
    }

    public FinalSessionProperties setGenerateSession(FinalGenerateSession generateSession) {
        this.generateSession = generateSession;
        return this;
    }
}
