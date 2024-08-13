package com.example.demo.controller.utils;

import org.springframework.http.HttpHeaders;

public class ControllerUtils {

    public static HttpHeaders redirect(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", url);
        return headers;
    }
}
