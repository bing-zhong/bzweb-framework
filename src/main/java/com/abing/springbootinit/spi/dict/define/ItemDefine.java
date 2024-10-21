package com.abing.springbootinit.spi.dict.define;


import com.abing.springbootinit.spi.dict.EnumDict;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 14:55
 * @Description
 */
public interface ItemDefine extends EnumDict<String> {

    String getText();
    String getValue();
}
