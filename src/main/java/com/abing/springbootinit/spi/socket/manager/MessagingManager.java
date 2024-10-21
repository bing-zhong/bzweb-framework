package com.abing.springbootinit.spi.socket.manager;

import com.abing.springbootinit.spi.socket.resp.Message;
import reactor.core.publisher.Flux;

/**
 * @Author CaptainBing
 * @Date 2024/8/31 14:44
 * @Description
 */
public interface MessagingManager {

    Flux<Message> subscribe(MessagingRequest request);


}
