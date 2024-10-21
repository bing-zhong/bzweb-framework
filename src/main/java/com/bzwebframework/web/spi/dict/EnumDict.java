package com.bzwebframework.web.spi.dict;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 14:02
 * @Description
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface EnumDict<V> {

    V getValue();

    /**
     * 枚举字典选项的文本,通常为中文
     *
     * @return 枚举的文本
     */
    String getText();

}
