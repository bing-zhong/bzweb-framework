package com.bzwebframework.web.spi.socket.manager;

import com.bzwebframework.web.spi.socket.resp.Message;
import reactor.core.publisher.Flux;

/**
 * @Author CaptainBing
 * @Date 2024/8/31 14:44
 * @Description
 */
public interface MessagingManager {

    Flux<Message> subscribe(MessagingRequest request);


}
