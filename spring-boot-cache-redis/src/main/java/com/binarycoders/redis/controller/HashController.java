package com.binarycoders.redis.controller;

import com.binarycoders.redis.service.HashService;
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

    @GetMapping(value = "/{text}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity<String> generate(@PathVariable final String text) {
        return ResponseEntity.ok(hashService.generateMd5(text));
    }
}
