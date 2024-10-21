package com.abing.springbootinit.spi.utils.redisson;

import com.abing.springbootinit.common.ErrorCode;
import com.abing.springbootinit.exception.BusinessException;
import com.abing.springbootinit.spi.utils.redisson.config.RedissonConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @Author CaptainBing
 * @Date 2024/9/5 14:40
 * @Description
 */
@Component
@RequiredArgsConstructor
public class LockManager {

    private final RedissonClient redissonClient;

    public <T> T executeWithLockThrows(String key, int waitTime, TimeUnit unit, RedissonConfig.SupplierThrow<T> supplier) throws Throwable {
        RLock lock = redissonClient.getLock(key);
        boolean lockSuccess = lock.tryLock(waitTime, unit);
        if (!lockSuccess) {
            throw new BusinessException(ErrorCode.LOCK_LIMIT);
        }
        try {
            //执行锁内的代码逻辑
            return supplier.get();
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @SneakyThrows
    public <T> T executeWithLock(String key, int waitTime, TimeUnit unit, Supplier<T> supplier) {
        return executeWithLockThrows(key, waitTime, unit, supplier::get);
    }

    public <T> T executeWithLock(String key, Supplier<T> supplier) {
        return executeWithLock(key, -1, TimeUnit.MILLISECONDS, supplier);
    }

}
