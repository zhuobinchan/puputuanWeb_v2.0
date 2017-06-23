package com.puputuan.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/14.
 */
public class RestClientUtils {
    private static RestTemplate restTemplate = new RestTemplate();

    private RestClientUtils() {
    }

    public static String getResourcesByString(String url, HttpEntity<MultiValueMap<String, Object>> request) throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        if (responseEntity.getStatusCodeValue() == 200) {
            return responseEntity.getBody();
        } else {
            throw new Exception("url:" + url + "  remote service has error");
        }
    }

    public static String getResourcesByUri(URI url, HttpEntity<MultiValueMap<String, Object>> request) throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        if (responseEntity.getStatusCodeValue() == 200) {
            return responseEntity.getBody();
        } else {
            throw new Exception("url:" + url + "  remote service has error");
        }
    }

    public static String postResources(String url, HttpEntity<MultiValueMap<String, Object>> request) throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        if (responseEntity.getStatusCodeValue() == 200) {
            return responseEntity.getBody();
        } else {
            throw new Exception("url:" + url + "  remote service has error");
        }
    }
}
