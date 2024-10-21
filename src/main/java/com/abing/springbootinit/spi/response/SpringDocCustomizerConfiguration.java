package com.abing.springbootinit.spi.response;

import com.abing.springbootinit.common.BaseResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springdoc.core.ReturnTypeParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @Author CaptainBing
 * @Date 2024/9/11 10:07
 * @Description
 */
@Configuration
public class SpringDocCustomizerConfiguration {

    @Bean
    public ReturnTypeParser returnTypeParser() {
        return new ReturnTypeParser() {
            @Override
            public Type getReturnType(MethodParameter methodParameter) {
                Type returnType = ReturnTypeParser.super.getReturnType(methodParameter);
                Class<?> parameterType = methodParameter.getParameterType();
                // 资源文件或者已经被包装了, 直接返回
                if (parameterType.isAssignableFrom(Resource.class) || parameterType.isAssignableFrom(BaseResponse.class)) {
                    return returnType;
                }
                // 分页特殊处理, 转为Page类
                if (parameterType.isAssignableFrom(Page.class) && returnType instanceof ParameterizedType) {
                    Optional<Type> t = TypeUtils.getTypeArguments((ParameterizedType) returnType)
                                                .values().stream().findFirst();
                    Type type = t.orElse(Object.class);
                    return TypeUtils.parameterize(BaseResponse.class, TypeUtils.parameterize(Page.class, type));
                }
                // void转为Res<Object>
                if (parameterType.isAssignableFrom(void.class)) {
                    return TypeUtils.parameterize(BaseResponse.class, Object.class);
                }
                // 未包装类型 ==> BaseResponse
                return TypeUtils.parameterize(BaseResponse.class, returnType);
            }
        };
    }
}
