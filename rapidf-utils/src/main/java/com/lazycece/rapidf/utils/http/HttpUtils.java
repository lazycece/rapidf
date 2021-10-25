/*
 *    Copyright 2021 lazycece<lazycece@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.lazycece.rapidf.utils.http;

import com.lazycece.rapidf.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lazycece
 * @date 2021/10/24
 */
public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public static String queryString(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return builder.substring(1);
    }

    public static <T> Response<T> doGet(String url, Map<String, String> header, Map<String, String> query, Class<T> clazz) {
        int code = -1;
        BufferedReader bufferedReader = null;
        Response<T> response = null;
        try {
            if (query != null && !query.isEmpty()) {
                url = !url.contains("?") ? String.format("%s?%s", url, queryString(query)) : String.format("%s&%s", url, queryString(query));
            }
            LOGGER.info("请求接口：{}", url);
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod(HttpMethod.GET.name());
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            code = connection.getResponseCode();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), CHARSET));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            String result = builder.toString();
            LOGGER.info("接口响应：code：{}，result：{}", code, result);
            if (clazz == String.class) {
                response = new Response(code, result, connection.getHeaderFields());
            } else {
                response = new Response<>(code, JsonUtils.parseObject(result, clazz), connection.getHeaderFields());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response = new Response(code, e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return response;
    }

    public static <T> Response<T> doGet(String url, Map<String, String> query, Class<T> clazz) {
        return doGet(url, null, query, clazz);
    }


    public static <T> Response<T> doPost(String url, Map<String, String> header, Map<String, String> query, String body, Class<T> clazz) {
        int code = -1;
        BufferedReader bufferedReader = null;
        Response<T> response = null;
        try {
            String queryString = query != null ? queryString(query) : null;
            if (queryString != null) {
                url = !url.contains("?") ? String.format("%s?%s", url, queryString(query)) : String.format("%s&%s", url, queryString(query));
            }
            LOGGER.info("请求接口：{}，body：{}", url, body);
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod(HttpMethod.POST.name());
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (body != null) {
                connection.getOutputStream().write(body.getBytes(CHARSET));
            }
            code = connection.getResponseCode();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), CHARSET));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            String result = builder.toString();
            LOGGER.info("接口响应：code：{}，result：{}", code, result);
            if (clazz == String.class) {
                response = new Response(code, result, connection.getHeaderFields());
            } else {
                response = new Response<>(code, JsonUtils.parseObject(result, clazz), connection.getHeaderFields());

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response = new Response(code, e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return response;
    }

    public static <T> Response<T> doPostWithJson(String url, Map<String, String> query, Map<String, String> body, Class<T> clazz) {
        String bodyString = JsonUtils.toJSONString(body);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json; charset=UTF-8");
        return doPost(url, header, query, bodyString, clazz);
    }

    public static <T> Response<T> doPostWithForm(String url, Map<String, String> query, Map<String, String> body, Class<T> clazz) {
        String bodyString = queryString(body);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        return doPost(url, header, query, bodyString, clazz);
    }

    public static <T> Response<T> doPostWithXml(String url, Map<String, String> query, Map<String, String> body, Class<T> clazz) {
        String bodyString = queryString(body);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/writexml; charset=UTF-8");
        return doPost(url, header, query, bodyString, clazz);
    }

    public static class Response<T> {

        private int code;
        /**
         * 响应内容
         */
        private T data;

        private Map<String, List<String>> header;

        private Exception exception;

        public Response() {
        }

        public Response(int code, T data) {
            this.code = code;
            this.data = data;
        }

        public Response(int code, T data, Map<String, List<String>> header) {
            this.code = code;
            this.data = data;
            this.header = header;
        }

        public Response(int code, Exception exception) {
            this.code = code;
            this.exception = exception;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

        public Map<String, List<String>> getHeader() {
            return header;
        }

        public void setHeader(Map<String, List<String>> header) {
            this.header = header;
        }
    }

    public enum HttpMethod {
        GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE;
    }
}

