package com.bzwebframework.web.spi.socket;

import com.bzwebframework.web.spi.socket.manager.MessagingRequest;
import com.bzwebframework.web.spi.socket.subscribe.SubscriptionProvider;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @Author CaptainBing
 * @Date 2024/8/31 16:55
 * @Description
 */
@Component
public class TestProvider implements SubscriptionProvider {


    @Override
    public String id() {
        return "1234";
    }

    @Override
    public String name() {
        return "test";
    }

    @Override
    public String[] getTopicPattern() {
        return new String[]{
                "/test2"
        };
    }

    @Override
    public Flux<?> subscribe(MessagingRequest messagingRequest) {

        return Flux.interval(Duration.ofSeconds(3))
                   .flatMap(i-> Mono.just("test2"));
    }
}
