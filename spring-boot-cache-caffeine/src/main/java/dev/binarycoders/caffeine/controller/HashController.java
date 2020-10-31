package dev.binarycoders.caffeine.controller;

import dev.binarycoders.caffeine.service.HashService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/hashes")
@AllArgsConstructor
public class HashController {

    private final HashService hashService;

    @GetMapping(value = "/spring/{text}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity<String> generateSpringCache(@PathVariable final String text) {
        return ResponseEntity.ok(hashService.generateMd5SpringCache(text));
    }

    @GetMapping(value = "/manual/{text}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity<String> generateManualCache(@PathVariable final String text) {
        return ResponseEntity.ok(hashService.generateMd5ManualCache(text));
    }
}
