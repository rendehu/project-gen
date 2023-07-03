package ${groupId}.base.concurrent;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * thread  context 线程相关操作
 * @author rende.hu
 * @since 2021-12-23 10:39:04
 */
@Component
@Slf4j
public class ThreadContext implements InitializingBean {

    private static final TransmittableThreadLocal<Map<ThreadLocalKey, Object>> THREAD_LOCAL_MAP = new TransmittableThreadLocal<>();

    private ExecutorService pools;

    @Value("${pool_core_pool_size}")
    private Integer corePoolSize;

    @Value("${pool_max_pool_size}")
    private Integer maxPoolSize;

    @Value("${pool_keep_alive_time}")
    private Integer keepAliveTime;

    public static void set(ThreadLocalKey key, Object value) {
        Map<ThreadLocalKey, Object> rstMap = copyMap(THREAD_LOCAL_MAP.get());
        rstMap.put(key, value);
        THREAD_LOCAL_MAP.set(rstMap);
    }

    public static Object get(ThreadLocalKey key) {
        Map<ThreadLocalKey, Object> rstMap = THREAD_LOCAL_MAP.get();
        if (rstMap != null) {
            return rstMap.get(key);
        }
        return null;
    }


    public static <T> T get(ThreadLocalKey key, Class<T> clazz) {
        return (T) get(key);
    }

    public void submit(Runnable runnable) {
        this.pools.execute(runnable);
    }

    public <V> Future<V> submit(Callable<V> callable) {
        return this.pools.submit(callable);
    }

    private static final Map<ThreadLocalKey, Object> copyMap(Map<ThreadLocalKey, Object> rstMap) {
        Map<ThreadLocalKey, Object> map = Maps.newHashMap();
        if (rstMap != null) {
            map.putAll(rstMap);
        }
        return map;
    }

    @Override
    public void afterPropertiesSet() {
        this.pools = createNewPools("system");
        log.info("System thread poll init success!! corePoolSize:{},maxPoolSize:{},keepAliveTime:{}s", this.corePoolSize, this.maxPoolSize, this.keepAliveTime);
    }

    public Executor getPools() {
        return this.pools;
    }

    /**
     * 创建新的线程池
     *
     * @param bizName
     * @return
     */
    public ExecutorService createNewPools(String bizName) {
        return createNewPools(bizName, this.corePoolSize, this.maxPoolSize);
    }

    public ExecutorService createNewPools(String bizName, int corePoolSize, int maxPoolSize) {
        ThreadFactoryBuilder builder = ThreadFactoryBuilder.create();
        builder.setDaemon(true)
                .setNamePrefix(bizName + "-thread-pool")
                .setPriority(6)
                .setUncaughtExceptionHandler((t, e) -> log.error("UncaughtException for thread:" + t.getName(), e));
        ExecutorService originPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, this.keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), builder.build());
        return TtlExecutors.getTtlExecutorService(originPool);
    }
}
