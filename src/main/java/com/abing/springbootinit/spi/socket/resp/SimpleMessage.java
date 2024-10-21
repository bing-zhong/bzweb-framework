package com.abing.springbootinit.spi.socket.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author CaptainBing
 * @Date 2024/8/31 14:24
 * @Description
 */
@Getter
@Setter
@AllArgsConstructor
public class SimpleMessage implements Message{

    private String requestId;

    private String topic;

    private Object payload;

    private Message.Type type;

    private String message;


}
