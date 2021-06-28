package com.aroquega.hashalgorithms.controllers;

import com.aroquega.hashalgorithms.algorithms.MD5;
import com.aroquega.hashalgorithms.algorithms.SHA256;
import com.aroquega.hashalgorithms.models.HashGetResponse;
import com.aroquega.hashalgorithms.models.MessagePostRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.StandardCharsets;

@RestController
public class HashControllers {

    @GetMapping("/md4")
    public String hashWithMd4(){
        return "md4";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/md5")
    public HashGetResponse hashWithMd5(@RequestBody MessagePostRequest request){
        var hashValue = MD5.toHexString(MD5.computeMD5(request.getMessage().getBytes()));
        var response = new HashGetResponse();
        response.hashValue = hashValue;
        return response;
    }

    @GetMapping("/sha1")
    public String hashWithSha1(){
        return "sha1";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/sha256")
    public HashGetResponse sha512(@RequestBody MessagePostRequest request){
        var hashValue =  MD5.toHexString(SHA256.hash(request.getMessage().getBytes()));
        var response = new HashGetResponse();
        response.hashValue = hashValue;
        return response;
    }

    @GetMapping("/hmac")
    public String hashWithHmac(@RequestParam String text){
        return "hmac";
    }
}
