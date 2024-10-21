package com.bzwebframework.web.spi.response;

import cn.hutool.json.JSONUtil;
import com.bzwebframework.web.common.BaseResponse;
import com.bzwebframework.web.common.ResultUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author CaptainBing
 * @Date 2024/9/11 10:07
 * @Description
 */
@RestControllerAdvice
@ConditionalOnProperty(prefix = "bzweb.response-wrapper", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConfigurationProperties(prefix = "bzweb.response-wrapper")
public class BaseResponseWrapperAdvice implements ResponseBodyAdvice<Object> {


    @Setter
    @Getter
    private Set<String> excludes = new HashSet<>();

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {

        if (methodParameter.getMethod() == null) {
            return true;
        }

        RequestMapping mapping = methodParameter.getMethodAnnotation(RequestMapping.class);
        if (mapping == null) {
            return false;
        }

        for (String produce : mapping.produces()) {
            MimeType mimeType = MimeType.valueOf(produce);
            if (MediaType.TEXT_EVENT_STREAM.includes(mimeType) ||
                    MediaType.APPLICATION_STREAM_JSON.includes(mimeType)) {
                return false;
            }
        }

        if (!CollectionUtils.isEmpty(excludes) && methodParameter.getMethod() != null) {

            String typeName = methodParameter.getMethod().getDeclaringClass().getName() + "." + methodParameter
                    .getMethod()
                    .getName();
            for (String exclude : excludes) {
                if (typeName.startsWith(exclude)) {
                    return false;
                }
            }
        }

        Class<?> returnType = methodParameter.getMethod().getReturnType();
        boolean isAlreadyResponse = returnType == BaseResponse.class || returnType == ResponseEntity.class;

        return !isAlreadyResponse;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof String){
            return JSONUtil.toJsonStr(ResultUtils.success(body));
        }
        return ResultUtils.success(body);
    }
}
