package com.noya.common.config;

import com.noya.rbac.common.interceptor.LoginInterceptor;
import com.noya.websocket.SystemMsgWebSocketHandler;
import com.noya.websocket.WebSocketHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author QJC
 * @version V1.0
 * @Description:
 * spring-boot有自己的一套web端拦截机制，
 * 若需要看到swagger发布的api文档界面，需要做一些特殊的配置，
 * 将springfox-swagger-ui包中的ui界面暴露给spring-boot资源环境。
 * @date 2018/3/7
 */
@Configuration
@EnableWebSocket
public class WebMvcConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
    /**
     * 增加websocket配置
     * @param webSocketHandlerRegistry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(systemMsgWebSocketHandler(),"/websocket/upcoming.do")
                .addInterceptors(new WebSocketHandlerInterceptor()).setAllowedOrigins("*");

        webSocketHandlerRegistry.addHandler(systemMsgWebSocketHandler(), "/sockjs/upcoming.do")
                .addInterceptors(new WebSocketHandlerInterceptor()).setAllowedOrigins("*")
                .withSockJS();
    }

    @Bean
    public SystemMsgWebSocketHandler systemMsgWebSocketHandler(){
        return new SystemMsgWebSocketHandler();
    }
}