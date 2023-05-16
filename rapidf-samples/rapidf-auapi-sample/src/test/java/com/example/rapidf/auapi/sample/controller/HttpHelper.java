package com.example.rapidf.auapi.sample.controller;

import com.lazycece.au.api.params.ParamsHolder;
import com.lazycece.au.api.params.utils.JsonUtils;
import com.lazycece.au.api.params.utils.SaltUtils;
import com.lazycece.au.api.token.TokenHolder;
import com.lazycece.rapidf.restful.response.RespData;
import com.lazycece.rapidf.restful.response.RespStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lazycece
 */
public class HttpHelper {

    public static final String TOKEN_HEADER = "TOKEN-HEADER";
    private static final HttpHelper HTTP_HELPER = new HttpHelper();
    private ParamsHolder paramsHolder;
    private TokenHolder tokenHolder;
    private RestTemplate restTemplate;
    private String token = "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6STFOaUo5LmV5SnpkV0lpT2lKeVR6QkJRbGhPZVVGRE9XcGlNakIxWWtkR05tVlhUbXhaTWxWMVkyMUdkMkZYVW0xTWJVWXhXVmhDY0V4dFZqUmtSMVoxWXpKc2RtSnBOVlpqTWxaNVZUTldhV0Z0Vm1wa1NqVndlRFU1ZVdJM05rbEJaMEZEVkVGQlIyUllUbXhqYTJ4clpFRkJVMVJIY0doa2JVVjJZa2RHZFZwNU9WUmtTRXB3WW0xak4xUkJRVWxrV0U1c1kyMDFhR0pYVm5oQlNEUkJRVmhvZDJSQlFVaFBSR2QzVFVSbk5FMUlVVUZEUjNob1pXNXNhbHBYVG13aUxDSnBjM01pT2lKVVQwdEZUaTFKVTFOVlJWSWlMQ0psZUhBaU9qRTJPRFF5TlRVeE9EVXNJbWxoZENJNk1UWTROREkxTXpNNE5YMC55UHBpckYzWkkySE44V3A2WURwaVRqUHMzRlZWM2JUR0ZYSzV1Z1NmaEd3=";

    private HttpHelper() {
        this("http://127.0.0.1:8888/au-api");
    }

    private HttpHelper(String rootUri) {
        restTemplate = new RestTemplateBuilder()
                .rootUri(rootUri)
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();
        paramsHolder = ParamsHolder.build("75HVYG0VQVDEYPLLODZUX99ZCV333EKY");
    }

    public static HttpHelper getInstance() {
        return HTTP_HELPER;
    }

    public ParamsHolder getParamsHolder() {
        return paramsHolder;
    }

    public HttpHelper token(String token) {
        this.token = token;
        return this;
    }

    public <T> T doGet(String url, Object data, Class<T> clazz) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(TOKEN_HEADER, token);
        HttpEntity entity = new HttpEntity<>(null, headers);
        StringBuilder urlBuilder = new StringBuilder(url + "?");
        for (Map.Entry<String, String> entry : getParams(data).entrySet()) {
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                urlBuilder.toString(), HttpMethod.GET, entity, String.class);
        return parseResponse(responseEntity.getBody(), clazz);
    }

    public <T> T doPost(String url, Object data, Class<T> clazz) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(TOKEN_HEADER, token);
        HttpEntity entity = new HttpEntity<>(getParams(data), headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        return parseResponse(response, clazz);
    }

    public <T> T doPostJson(String url, Object data, Class<T> clazz) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(TOKEN_HEADER, token);
        HttpEntity entity = new HttpEntity<>(getParams(data), headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        return parseResponse(response, clazz);
    }

    public ResponseEntity<RespData> doPostJson(String url, Object data) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(TOKEN_HEADER, token);
        HttpEntity entity = new HttpEntity<>(getParams(data), headers);
        return restTemplate.postForEntity(url, entity, RespData.class);
    }

    public <T> T doUpload(String url, String filepath, String fileField, Object data, Class<T> clazz) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add(TOKEN_HEADER, token);
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        getParams(data).forEach(multiValueMap::add);
        FileSystemResource resource = new FileSystemResource(new File(filepath));
        multiValueMap.add(fileField, resource);
        HttpEntity entity = new HttpEntity<>(multiValueMap, headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        return parseResponse(response, clazz);
    }

    private HashMap<String, String> getParams(Object data) throws Exception {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("time", String.valueOf(System.currentTimeMillis()));
        paramsMap.put("deviceId", "unit-test");
        paramsMap.put("version", "2.0.0");
        paramsMap.put("versionCode", "200");
        String salt = SaltUtils.generateSalt();
        paramsMap.put("salt", salt);
        if (data != null) {
            paramsMap.put("data", paramsHolder.encode(salt, JsonUtils.toJSONString(data)));
        }
        paramsMap.put("sign", paramsHolder.sign(paramsMap));
        return paramsMap;
    }

    private <T> T parseResponse(String response, Class<T> clazz) throws Exception {
        ResponseMap responseMap = JsonUtils.parseObject(response, ResponseMap.class);
        if (RespStatus.SUCCESS.getCode() != (int) responseMap.get(ResponseMap.CODE_FIELD)) {
            throw new IllegalArgumentException("code: " + responseMap.get(ResponseMap.CODE_FIELD)
                    + " message: " + responseMap.get(ResponseMap.MESSAGE_FIELD));
        }
        if (null == responseMap.get(ResponseMap.DATA_FIELD)) {
            System.out.println("response data is null");
            return null;
        }
        String decodeData = paramsHolder.decode((String) responseMap.get(ResponseMap.SALT_FIELD)
                , (String) responseMap.get(ResponseMap.DATA_FIELD));
        System.out.println(decodeData);
        return JsonUtils.parseObject(decodeData, clazz);
    }
}