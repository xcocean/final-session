package top.lingkang.sessioncore.base.impl;

import top.lingkang.sessioncore.base.FinalRepository;
import top.lingkang.sessioncore.config.FinalSessionProperties;
import top.lingkang.sessioncore.wrapper.FinalSession;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class FinalMemoryRepository implements FinalRepository {
    private ConcurrentMap<String, FinalSession> map = new ConcurrentHashMap<>();
    private FinalSessionProperties properties;
    public FinalMemoryRepository() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (map.size() > 0) {
                    long reduce = System.currentTimeMillis() - properties.getMaxValidTime() + 300000;// 预留5分钟
                    for (Map.Entry<String, FinalSession> entry : map.entrySet()) {
                        if (entry.getValue().getLastAccessedTime() < reduce) {
                            // 淘汰此会话
                            map.remove(entry.getKey());
                        }
                    }
                }
            }
        }, 300000, 28800000);// 8小时执行一次
    }

    @Override
    public FinalSession getSession(String id) {
        return map.get(id);
    }

    @Override
    public void setSession(String id, FinalSession session) {
        map.put(id, session);
    }

    @Override
    public void setFinalSessionProperties(FinalSessionProperties properties) {
        this.properties=properties;
    }

    @Override
    public void destroy() {

    }
}
