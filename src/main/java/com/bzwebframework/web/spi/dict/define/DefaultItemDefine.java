package com.bzwebframework.web.spi.dict.define;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 14:57
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultItemDefine implements ItemDefine {

    private String text;
    private String value;
    private String comments;
    private int ordinal;

}
