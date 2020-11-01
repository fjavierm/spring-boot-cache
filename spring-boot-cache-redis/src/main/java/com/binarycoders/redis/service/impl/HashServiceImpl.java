package com.binarycoders.redis.service.impl;

import com.binarycoders.redis.service.HashService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class HashServiceImpl implements HashService {

    @Cacheable(value = "md5-cache")
    @Override
    public String generateMd5(final String text) {
        log.info("Generating the MD5 hash...");

        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(text.getBytes());

            return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to get MD5 instance");
        }
    }
}
