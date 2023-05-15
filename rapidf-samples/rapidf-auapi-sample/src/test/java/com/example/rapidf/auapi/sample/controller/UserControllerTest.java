package com.example.rapidf.auapi.sample.controller;

import com.example.rapidf.auapi.sample.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/**
 * @author lazycece
 */
public class UserControllerTest {

    @Test
    public void testLogin() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setUsername("lazycece");
        req.setPassword("000000");
        ResponseEntity responseEntity = HttpHelper.getInstance().doPostJson("/u/login", req);
        System.out.println(responseEntity.getHeaders().get(HttpHelper.TOKEN_HEADER));
    }

    @Test
    public void testInfo() throws Exception {
        HttpHelper.getInstance().doGet("/u/info",null,String.class);
    }
}
