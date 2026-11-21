package org.korolev.autopartsstore.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class CacheConfig {

    @Bean
    @ConditionalOnProperty(name = "app.cache.type", havingValue = "simple")
    public CacheManager inMemoryCacheManager() {
        return new ConcurrentMapCacheManager(CacheNames.PRODUCTS, CacheNames.CATEGORIES);
    }
}
