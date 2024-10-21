package com.bzwebframework.web.spi.utils.redisson.aop;

import com.bzwebframework.web.spi.utils.redisson.LockManager;
import com.bzwebframework.web.spi.utils.redisson.RateLimitManager;
import com.bzwebframework.web.spi.utils.redisson.SpELUtils;
import com.bzwebframework.web.spi.utils.redisson.annotation.RedissonLock;
import com.bzwebframework.web.spi.utils.redisson.annotation.RedissonRateLimit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RateIntervalUnit;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author CaptainBing
 * @Date 2024/9/5 14:38
 * @Description
 */
@Slf4j
@Aspect
@Component
@Order(0)//确保比事务注解先执行，分布式锁在事务外
@RequiredArgsConstructor
public class RedissonOpsAop {

    private final LockManager lockManager;

    private final RateLimitManager rateLimitManager;

    @Around("@annotation(com.bzwebframework.web.spi.utils.redisson.annotation.RedissonLock)")
    public Object lockAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        //默认方法限定名+注解排名（可能多个）
        String prefix = StringUtils.isEmpty(redissonLock.prefixKey()) ? SpELUtils.getMethodKey(method) : redissonLock.prefixKey();
        // el 表达式解析 key
        String key = SpELUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockManager.executeWithLockThrows(prefix + ":" + key, redissonLock.waitTime(), redissonLock.unit(), joinPoint::proceed);
    }


    @Around("@annotation(com.bzwebframework.web.spi.utils.redisson.annotation.RedissonRateLimit)")
    public Object limitAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedissonRateLimit redissonRateLimit = method.getAnnotation(RedissonRateLimit.class);
        long rate = redissonRateLimit.rate();
        long rateInterval = redissonRateLimit.rateInterval();
        RateIntervalUnit rateIntervalUnit = redissonRateLimit.rateIntervalUnit();
        long permit = redissonRateLimit.permit();
        //默认方法限定名+注解排名（可能多个）
        String prefix = StringUtils.isEmpty(redissonRateLimit.prefixKey()) ? SpELUtils.getMethodKey(method) : redissonRateLimit.prefixKey();
        // el 表达式解析 key
        String key = SpELUtils.parseSpEl(method, joinPoint.getArgs(), redissonRateLimit.key());

        return rateLimitManager.executeWithRateLimitThrow(prefix + ":" + key, rate, rateInterval,rateIntervalUnit,permit, joinPoint::proceed);
    }

}
