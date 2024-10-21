package com.abing.springbootinit.spi.socket;

import cn.hutool.json.JSONUtil;
import com.abing.springbootinit.spi.socket.manager.MessagingManager;
import com.abing.springbootinit.spi.socket.manager.MessagingRequest;
import com.abing.springbootinit.spi.socket.resp.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author CaptainBing
 * @Date 2024/8/30 17:25
 * @Description
 */
@Slf4j
@RequiredArgsConstructor
public class WebSocketMessagingHandler extends AbstractWebSocketHandler {

    private final MessagingManager messagingManager;

    /**
     * 订阅信息
     */
    Map<String, Disposable> subs = new ConcurrentHashMap<>();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {

        if (message instanceof PongMessage) {
            return;
        }
        if (message instanceof PingMessage) {
            session.sendMessage(new PongMessage());
        }
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            //解析request请求
            MessagingRequest request = JSONUtil.toBean(textMessage.getPayload(), MessagingRequest.class);
            if (Objects.isNull(request)) {
                return;
            }
            //payload ping -> pong
            if (request.getType() == MessagingRequest.Type.ping) {
                sendToSession(session, Message.pong(request.getId()));
                return;
            }
            if (StringUtils.isEmpty(request.getId())) {
                sendToSession(session, Message.error(request.getType().name(), null, "id不能为空"));
                return;
            }
            if (request.getType() == MessagingRequest.Type.sub) {

                //重复订阅,忽略
                Disposable old = subs.get(request.getId());
                if (Objects.nonNull(old) && !old.isDisposed()) {
                    log.warn("重复订阅:{}", request);
                    return;
                }

                Disposable sub = messagingManager.subscribe(request)
                                                 .onErrorResume(err -> Mono.just(Message.error(request.getId(), request.getTopic(), err)))
                                                 .map(msg -> sendToSession(session, msg))
                                                 .doFinally(signalType -> {
                                                     //连接断开时，释放全部订阅
                                                     subs.remove(request.getId());
                                                 })
                                                 .subscribe();
                if (!sub.isDisposed()) {
                    subs.put(request.getId(), sub);
                }
            } else if (request.getType() == MessagingRequest.Type.unsub) {
                //取消订阅则释放订阅
                Disposable disposable = subs.remove(request.getId());
                if (Objects.nonNull(disposable)) {
                    disposable.dispose();
                }
            } else {
                sendToSession(session, Message.error(request.getId(), request.getTopic(), "不支持的类型:" + request.getType()));
            }
        }
    }

    private Message sendToSession(WebSocketSession session, Message message) {
        try {
            session.sendMessage(new TextMessage(JSONUtil.toJsonStr(message)));
            return message;
        } catch (IOException e) {
            throw new RuntimeException("发送消息失败", e);
        }
    }

}
