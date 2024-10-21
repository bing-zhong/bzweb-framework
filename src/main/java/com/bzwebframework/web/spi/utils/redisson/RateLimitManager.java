package com.bzwebframework.web.spi.utils.redisson;

import com.bzwebframework.web.common.ErrorCode;
import com.bzwebframework.web.exception.BusinessException;
import com.bzwebframework.web.spi.utils.redisson.config.RedissonConfig;
import lombok.RequiredArgsConstructor;
import org.redisson.api.*;
import org.springframework.stereotype.Component;


/**
 * @Author CaptainBing
 * @Date 2024/9/5 15:44
 * @Description
 */
@Component
@RequiredArgsConstructor
public class RateLimitManager {

    private final RedissonClient redissonClient;

    public <T> T executeWithRateLimitThrow(String key, long rate, long rateInterval, RateIntervalUnit rateIntervalUnit,long permit, RedissonConfig.SupplierThrow<T> supplier) throws Throwable{
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, rate, rateInterval, rateIntervalUnit);
        boolean isLimit = rateLimiter.tryAcquire(permit);
        if (!isLimit) {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
        }
        return supplier.get();
    }


}
