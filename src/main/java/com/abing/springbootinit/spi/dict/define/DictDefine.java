package com.abing.springbootinit.spi.dict.define;


import com.abing.springbootinit.spi.dict.EnumDict;

import java.io.Serializable;
import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 14:11
 * @Description
 */
public interface DictDefine extends Serializable {

    String getId();

    String getAlias();

    List<? extends EnumDict<?>> getItems();

}
