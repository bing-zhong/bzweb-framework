package com.abing.springbootinit.spi.socket.manager;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @Author CaptainBing
 * @Date 2024/8/31 14:10
 * @Description
 */
@Getter
@Setter
public class MessagingRequest {

    private String id;

    private Type type;

    private String topic;

    private Map<String,Object> parameter;

    @Generated
    public enum Type{
        pub,sub,unsub,ping;
    }


}
