package dev.binarycoders.ehcache.utils;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(final CacheEvent<?, ?> cacheEvent) {
        log.info("Type: {}, Key: {}, Old: {}, New: {}",
            cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
