package com.ihomefnt.o2o.common.configuration;

import com.ihomefnt.common.util.JsonUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.BeanHelper;
import com.ihomefnt.o2o.intf.manager.util.common.cache.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 */
@Configuration
@EnableCaching
public class SpringCacheConfig extends CachingConfigurerSupport {
    Logger logger = LoggerFactory.getLogger(SpringCacheConfig.class);

    private final static int NO_PARAM_KEY = 0;
    private String keyPrefix = "o2o-api";

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<Cache> cacheList = new ArrayList<>(1);
        RedisCache redisCache = new RedisCache();
        redisCache.setName("o2o-api");
        redisCache.setRedisTemplate(redisTemplate);
        cacheList.add(redisCache);

        cacheManager.setCaches(cacheList);
        return cacheManager;
    }

    @Bean
    public KeyGenerator springCacheKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                char sp = ':';
                StringBuilder strBuilder = new StringBuilder(30);
                strBuilder.append(keyPrefix);
                strBuilder.append(sp);
                // 类名
//        strBuilder.append(target.getClass().getSimpleName());
//        strBuilder.append(sp);
                // 方法名
                strBuilder.append(method.getName());
                strBuilder.append(sp);
                if (params.length > 0) {
                    // 参数值
                    for (Object object : params) {
                        if(object == null){
                            continue;
                        }
                        if (BeanHelper.isSimpleValueType(object.getClass())) {
                            strBuilder.append(object);
                            strBuilder.append(sp);
                        } else {
                            strBuilder.append(JsonUtil.toString(object));
                            strBuilder.append(sp);
                        }
                    }
                } else {
                    strBuilder.append(NO_PARAM_KEY);
                }
                return strBuilder.toString();
            }
        };
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache,
                                            Object key, Object value) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache,
                                            Object key) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache,
                                              Object key) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                RedisErrorException(exception, null);
            }
        };
        return cacheErrorHandler;
    }

    protected void RedisErrorException(Exception exception,Object key){
        logger.error("RedisErrorException：key=[{}]", key, exception);
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
