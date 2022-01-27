package top.lingkang.examplespringboot.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author 绫小路
 * @date 2021/1/30 21:48
 * @description
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {

    public static Map<String, WebSocketSession> users = new HashMap<>();

    /**
     * 连接之后
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.put(session.getId(), session);
        System.out.println("有人加入：id=" + session.getId() + "。当前在线人数" + users.size());
    }

    /**
     * 处理接收消息 message.getPayload()类型为string
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(message.getPayload());
        if (message.getPayload() instanceof String) {
            System.out.println("string");
        }
    }

    /**
     * 处理错误
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("发生异常：" + exception.getMessage());
    }

    /**
     * 连接关闭后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("CloseStatus" + closeStatus);
        users.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
