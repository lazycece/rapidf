package com.example.rapidf.auapi.sample.controller;

import com.example.rapidf.auapi.sample.request.BaseRequest;
import com.example.rapidf.auapi.sample.request.LoginRequest;
import com.lazycece.au.api.token.SubjectContext;
import com.lazycece.au.api.token.TokenHolder;
import com.lazycece.rapidf.auapi.extension.UserSubject;
import com.lazycece.rapidf.restful.response.RespData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author lazycece
 */
@RestController
@RequestMapping("/u")
@Slf4j
public class UserController {

    @Resource
    private TokenHolder tokenHolder;

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request, HttpServletResponse response) throws Exception {
        log.info("username -> {}, password = {}", request.getUsername(), request.getPassword());
        UserSubject subject = new UserSubject();
        subject.setUsername(request.getUsername());
        subject.setUserId("8800880");
        String token = tokenHolder.generateToken(subject);
        response.addHeader(tokenHolder.getTokenHeader(), token);
        return RespData.success();
    }

    @GetMapping("/info")
    public Object getUserInfo(String userId) {
        log.info("tester: userId = {}", userId);
        UserSubject subject = (UserSubject) SubjectContext.getContext();
        return RespData.success(subject);
    }

    @PostMapping("/subject")
    public Object testUserSubject(@RequestBody BaseRequest request) {
        return RespData.success(request);
    }
}
