package com.aroquega.hashalgorithms.controllers;

import com.aroquega.hashalgorithms.algorithms.MD5;
import com.aroquega.hashalgorithms.algorithms.SHA256;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class HashControllers {
    @GetMapping("/md5")
    public String hashWithMd5(@RequestParam String text){
        return MD5.toHexString(MD5.computeMD5(text.getBytes()));
    }

    @GetMapping("/sha256")
    public String sha512(@RequestParam String text){
        return MD5.toHexString(SHA256.hash(text.getBytes()));
    }
}
