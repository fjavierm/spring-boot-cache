package dev.binarycoders.caffeine.service;

public interface HashService {

    String generateMd5SpringCache(String text);

    String generateMd5ManualCache(String text);
}
