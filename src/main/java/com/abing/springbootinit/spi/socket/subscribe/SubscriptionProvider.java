package com.abing.springbootinit.spi.socket.subscribe;

import com.abing.springbootinit.spi.socket.manager.MessagingRequest;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author CaptainBing
 * @Date 2024/8/31 13:11
 * @Description
 */
public interface SubscriptionProvider {

    List<SubscriptionProvider> supports = new CopyOnWriteArrayList<>();

    String id();

    String name();

    String[] getTopicPattern();

    Flux<?> subscribe(MessagingRequest messagingRequest);


}
