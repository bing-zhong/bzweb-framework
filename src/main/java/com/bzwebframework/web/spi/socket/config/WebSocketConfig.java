package com.bzwebframework.web.spi.socket.config;

import com.bzwebframework.web.spi.socket.WebSocketMessagingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author CaptainBing
 * @Date 2024/8/31 15:33
 * @Description
 */
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {


    private final WebSocketMessagingHandler webSocketMessagingHandler;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册 socket处理器
        registry.addHandler(webSocketMessagingHandler, "/messaging/**")
                .setAllowedOrigins("*");
    }

}
