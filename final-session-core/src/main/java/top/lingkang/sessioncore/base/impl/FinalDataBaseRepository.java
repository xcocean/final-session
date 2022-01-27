package top.lingkang.sessioncore.base.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import top.lingkang.sessioncore.base.FinalRepository;
import top.lingkang.sessioncore.config.FinalSessionProperties;
import top.lingkang.sessioncore.utils.SerializationUtils;
import top.lingkang.sessioncore.wrapper.FinalSession;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lingkang
 * Created by 2022/1/27
 */
public class FinalDataBaseRepository implements FinalRepository {
    private static final Logger log = LoggerFactory.getLogger(FinalDataBaseRepository.class);
    private FinalSessionProperties properties;
    private JdbcTemplate jdbcTemplate;

    public FinalDataBaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FinalSession getSession(String id) {
        try {
            List<InputStream> ins = jdbcTemplate.query("select session from fs_session where id=?", new String[]{id}, new RowMapper() {
                @Override
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getBinaryStream(1);
                }
            });
            if (ins.isEmpty())
                return null;
            return (FinalSession) SerializationUtils.unSerialization(ins.get(0));
        } catch (Exception e) {
            throw new IllegalArgumentException("会话获取失败：", e);
        }
    }

    @Override
    public void setSession(String id, FinalSession session) {
        try {
            byte[] serialization = SerializationUtils.serialization(session);
            int update = jdbcTemplate.update("update fs_session set session=? where id=?", serialization, id);
            if (update == 0)
                jdbcTemplate.update("insert into fs_session(id,session) values(?,?)", id, serialization);
        } catch (IOException e) {
            throw new IllegalArgumentException("设置会话失败：", e);
        }
    }

    @Override
    public void setFinalSessionProperties(FinalSessionProperties pro) {
        this.properties = pro;
        long time = properties.getMaxValidTime() + properties.getReserveTime() + 300000L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date = new Date(System.currentTimeMillis() - time);
                int result = jdbcTemplate.update("delete from fs_session where update_time<?", format.format(date));
                log.info("清理数据库过期会话数：{} 个", result);
            }
        }, 5000, 28800000);// 8小时执行一次
    }

    @Override
    public void destroy() {

    }
}
