package com.bzwebframework.web.spi.dict.web;

import com.bzwebframework.web.spi.dict.DictionaryManager;
import com.bzwebframework.web.spi.dict.EnumDict;
import com.bzwebframework.web.spi.dict.define.DictDefine;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 15:17
 * @Description
 */
@RestController
@RequestMapping("/dictionary")
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryManager dictionaryManager;

    @GetMapping("/{id:.+}/items")
    @Operation(summary = "获取数据字段的所有选项")
    public List<? extends EnumDict<?>> getItemDefineById(@PathVariable String id) {
        return Optional.ofNullable(dictionaryManager.getDefine(id))
                       .map(DictDefine::getItems)
                       .orElse(new ArrayList<>());
    }

    @GetMapping("/_all")
    @Schema(description = "获取全部数据字典")
    public List<DictDefine> getAllDict() {
        return dictionaryManager.getAllDefine();
    }

}
