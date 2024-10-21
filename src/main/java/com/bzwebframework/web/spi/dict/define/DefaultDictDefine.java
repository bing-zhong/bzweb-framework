package com.bzwebframework.web.spi.dict.define;

import com.bzwebframework.web.spi.dict.EnumDict;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 14:13
 * @Description
 */
@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultDictDefine implements DictDefine {

    private static final long serialVersionUID = 20094004707177152L;

    private String id;

    private String alias;

    private List<? extends EnumDict<?>> items;


}
