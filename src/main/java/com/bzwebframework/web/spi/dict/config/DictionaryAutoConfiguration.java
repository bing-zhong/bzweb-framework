package com.bzwebframework.web.spi.dict.config;

import com.bzwebframework.web.spi.dict.DictionaryManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 11:27
 * @Description
 */
@Configuration
@EnableConfigurationProperties(DictionaryProperties.class)
public class DictionaryAutoConfiguration {

    @Bean
    public DictionaryManager defaultDictionaryService(DictionaryProperties dictionaryProperties) {
        DictionaryManager dictionaryManager = new DictionaryManager();
        dictionaryProperties.doScanEnum()
                            .stream()
                            .map(dictionaryManager::parseEnumDict)
                            .forEach(dictionaryManager::registerDefine);

        return new DictionaryManager();
    }

}
