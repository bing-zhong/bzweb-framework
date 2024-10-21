package com.bzwebframework.web.spi.utils.redisson.annotation;

import org.redisson.api.RateIntervalUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author CaptainBing
 * @Date 2024/9/5 15:42
 * @Description Redisson限流注解
 */
@Retention(RetentionPolicy.RUNTIME)//运行时生效
@Target(ElementType.METHOD)//作用在方法上
public @interface RedissonRateLimit {

    /**
     * key的前缀,默认取方法全限定名，除非我们在不同方法上对同一个资源做分布式锁，就自己指定
     *
     * @return key的前缀
     */
    String prefixKey() default "";

    /**
     * spring el 表达式
     * @return ratelimit 唯一键
     */
    String key();

    /**
     * 生成令牌数 默认每秒生成1个令牌
     *
     * @return 单位秒
     */
    long rate() default 1L;

    /***
     * 生成令牌速率
     * @return
     */
    long rateInterval() default 1L;

    /**
     * 生成令牌速率单位
     * @return
     */
    RateIntervalUnit rateIntervalUnit() default RateIntervalUnit.SECONDS;

    /**
     * 请求消耗令牌数
     * 默认每次请求消耗一个令牌
     * @return
     */
    long permit() default 1L;

}
