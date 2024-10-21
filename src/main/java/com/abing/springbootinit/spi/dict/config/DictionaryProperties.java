package com.abing.springbootinit.spi.dict.config;

import com.abing.springbootinit.spi.dict.EnumDict;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author CaptainBing
 * @Date 2024/9/2 11:41
 * @Description
 */
@Getter
@Setter
@ConfigurationProperties("starry.dict")
public class DictionaryProperties {

    /**
     * 枚举包路径
     */
    private Set<String> enumPackages = new HashSet<>();


    @SneakyThrows
    public List<Class> doScanEnum(){

        List<Class> classes = new ArrayList<>();
        CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        for (String enumPackage : enumPackages) {

            String path = "classpath*:" + ClassUtils.convertClassNameToResourcePath(enumPackage) + "/**/*.class";
            Resource[] resources = resourcePatternResolver.getResources(path);
            for (Resource resource : resources) {
                try {
                    MetadataReader reader = metadataReaderFactory.getMetadataReader(resource);
                    String name = reader.getClassMetadata().getClassName();
                    Class<?> clazz = ClassUtils.forName(name,null);
                    if (clazz.isEnum() && EnumDict.class.isAssignableFrom(clazz)) {
                        classes.add(clazz);
                    }
                } catch (Throwable e) {

                }
            }

        }
        metadataReaderFactory.clearCache();
        return classes;
    }

}
