package dev.binarycoders.caffeine.utils;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheEventLogger {

    public static RemovalListener<String, String> removalListener() {
        return (String key, String graph, RemovalCause cause) ->
            log.info("Key {} was removed ({})", key, cause);
    }
}
