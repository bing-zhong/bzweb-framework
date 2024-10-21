package com.bzwebframework.web.spi.dict;

import com.bzwebframework.web.spi.dict.define.DefaultDictDefine;
import com.bzwebframework.web.spi.dict.define.DefaultItemDefine;
import com.bzwebframework.web.spi.dict.define.DictDefine;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 11:44
 * @Description
 */
@Slf4j
public class DictionaryManager {

    protected static final Map<String, DictDefine> PARSED_DICT = new HashMap<>();

    public void registerDefine(DictDefine define) {
        if (define == null) {
            return;
        }
        PARSED_DICT.put(define.getId(), define);
    }

    public DictDefine parseEnumDict(Class<?> type) {

        Dict dict = type.getAnnotation(Dict.class);
        if (!type.isEnum()) {
            throw new UnsupportedOperationException("unsupported type " + type);
        }
        List<EnumDict<?>> items = new ArrayList<>();
        for (Object enumConstant : type.getEnumConstants()) {
            if (enumConstant instanceof EnumDict) {
                EnumDict enumDict = (EnumDict) enumConstant;
                items.add(enumDict);
            } else {
                Enum e = ((Enum) enumConstant);
                items.add(DefaultItemDefine.builder()
                                           .value(e.name())
                                           .text(e.name())
                                           .ordinal(e.ordinal())
                                           .build());
            }
        }

        DefaultDictDefine define = new DefaultDictDefine();
        if (dict != null) {
            define.setId(dict.value());
            define.setAlias(dict.alias());
        } else {

            String id = camelCase2UnderScoreCase(type.getSimpleName()).replace("_", "-");
            if (id.startsWith("-")) {
                id = id.substring(1);
            }
            define.setId(id);
            define.setAlias(type.getSimpleName());
        }
        define.setItems(items);
        log.trace("parse enum dict : {} as : {}", type, define.getId());
        return define;
    }


    public DictDefine getDefine(String id) {
        return PARSED_DICT.get(id);
    }

    public List<DictDefine> getAllDefine() {
        return (List<DictDefine>) PARSED_DICT.values();
    }

    public static String camelCase2UnderScoreCase(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

}
