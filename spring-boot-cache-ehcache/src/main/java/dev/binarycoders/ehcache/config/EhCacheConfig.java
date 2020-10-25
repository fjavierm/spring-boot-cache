package dev.binarycoders.ehcache.config;

import dev.binarycoders.ehcache.utils.CacheEventLogger;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
@EnableCaching
public class EhCacheConfig {

    @Bean
    CacheManager getCacheManager() {
        final CachingProvider provider = Caching.getCachingProvider();
        final CacheManager cacheManager = provider.getCacheManager();

        final CacheConfigurationBuilder<String, String> configurationBuilder =
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class, String.class,
                ResourcePoolsBuilder.heap(50)
                    .offheap(10, MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(10)));

        final CacheEventListenerConfigurationBuilder asynchronousListener = CacheEventListenerConfigurationBuilder
            .newEventListenerConfiguration(new CacheEventLogger(), EventType.CREATED, EventType.EXPIRED)
            .unordered().asynchronous();

        cacheManager.createCache("md5-cache",
            Eh107Configuration.fromEhcacheCacheConfiguration(configurationBuilder.withService(asynchronousListener)));

        return cacheManager;
    }
}
