package dev.binarycoders.caffeine.service.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import dev.binarycoders.caffeine.service.HashService;
import dev.binarycoders.caffeine.utils.CacheEventLogger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class HashServiceImpl implements HashService {

    private static final String MD5_CACHE_ID = "md5-cache";

    // It is used for the "ManualCache"
    final LoadingCache<String, String> md5Cache = Caffeine.newBuilder()
        .maximumSize(10)
        .expireAfterWrite(10, TimeUnit.SECONDS)
        .removalListener(CacheEventLogger.removalListener())
        .build(this::generateMd5Wrapper);

    @Cacheable(cacheNames = MD5_CACHE_ID)
    @Override
    public String generateMd5SpringCache(final String text) {
        log.info("The value was not cached by Spring");

        return generateMd5(text);
    }

    @Override
    public String generateMd5ManualCache(final String text) {
        return md5Cache.get(text);
    }

    private String generateMd5Wrapper(final String text) { // This only exist for demo purposes (unnecessary)
        log.info("The value was not cached manually");

        return generateMd5(text);
    }

    private String generateMd5(final String text) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(text.getBytes());

            return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to get MD5 instance");
        }
    }
}
