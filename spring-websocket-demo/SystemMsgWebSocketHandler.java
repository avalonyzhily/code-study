package com.noya.websocket;

import com.noya.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统中需要通过长连接进行通信的处理类
 * @Author HeMiaolin
 * @Description
 * @Date 2018/5/16 9:11
 */
@Slf4j
public class SystemMsgWebSocketHandler extends TextWebSocketHandler {
    private static final Map<String,WebSocketSession> users = new ConcurrentHashMap<>();


    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println("成功建立websocket连接!");
        String userId = (String) session.getAttributes().get(Constants.LOGIN_ACCOUNT_SESSION_ID);
        users.put(userId,session);
        System.out.println("当前线上用户数量:"+users.size());

        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        //TextMessage returnMessage = new TextMessage("成功建立socket连接，你将收到的离线");
        //session.sendMessage(returnMessage);
    }

    /**
     * 关闭连接时触发
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.debug("关闭websocket连接");
        String userId= (String) session.getAttributes().get(Constants.LOGIN_ACCOUNT_SESSION_ID);
        System.out.println("用户"+userId+"已退出！");
        users.remove(userId);
        System.out.println("剩余在线用户"+users.size());
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        super.handleTextMessage(session, message);

        /**
         * 收到消息，自定义处理机制，实现业务
         */
        System.out.println("服务器收到消息："+message);

        if(message.getPayload().startsWith("#anyone#")){ //单发某人

            sendMessageToUser((String)session.getAttributes().get(Constants.LOGIN_ACCOUNT_SESSION_ID), new TextMessage("服务器单发：" +message.getPayload())) ;

        }else if(message.getPayload().startsWith("#everyone#")){

            sendMessageToUsers(new TextMessage("服务器群发：" +message.getPayload()));

        }else{

        }

    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        log.debug("传输出现异常，关闭websocket连接... ");
        String userId= (String) session.getAttributes().get(Constants.LOGIN_ACCOUNT_SESSION_ID);
        users.remove(userId);
    }

    public boolean supportsPartialMessages() {

        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userId
     * @param message
     */
    public void sendMessageToUser(String userId, TextMessage message) {
        try {
            WebSocketSession userWebSocketSession = users.get(userId);
            if (userWebSocketSession.isOpen()) {
                userWebSocketSession.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (String userId : users.keySet()) {
            try {
                sendMessageToUser(userId, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
