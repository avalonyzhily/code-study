package com.noya.websocket;

import com.noya.common.constant.Constants;
import com.noya.rbac.vo.AccountsDto;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author HeMiaolin
 * @Description
 * @Date 2018/5/16 9:20
 */
public class WebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
                AccountsDto accountsDto = (AccountsDto) session.getAttribute(Constants.LOGIN_ACCOUNT_SESSION);  //一般直接保存user实体
                if (accountsDto!=null) {
                    attributes.put(Constants.LOGIN_ACCOUNT_SESSION_ID,accountsDto.getSysId());
                }else {
                    ((ServletServerHttpResponse)response).getServletResponse().setStatus(Constants.MISS_LOGIN_SESSION);
                    return false;
                }
            }else {
                //丢失session重定向
                ((ServletServerHttpResponse)response).getServletResponse().setStatus(Constants.MISS_LOGIN_SESSION);
                return false;
            }

        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
