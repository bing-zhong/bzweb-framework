package com.abing.springbootinit.spi.socket.manager;

import com.abing.springbootinit.spi.socket.resp.Message;
import com.abing.springbootinit.spi.socket.subscribe.SubscriptionProvider;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Flux;

/**
 * @Author CaptainBing
 * @Date 2024/8/31 14:56
 * @Description
 */
public class DefaultMessagingManager implements MessagingManager {

    private final static PathMatcher matcher = new AntPathMatcher();

    @Override
    public Flux<Message> subscribe(MessagingRequest request) {

        for (SubscriptionProvider provider : SubscriptionProvider.supports) {
            for (String pattern : provider.getTopicPattern()) {
                if (matcher.match(pattern, request.getTopic())) {
                    return provider.subscribe(request)
                                   .map(v -> Message.success(request.getId(), request.getTopic(), v));
                }
            }
        }
        throw new UnsupportedOperationException("error.unsupported_topic");
    }

    public void register(SubscriptionProvider provider) {
        SubscriptionProvider.supports.add(provider);
    }

}
