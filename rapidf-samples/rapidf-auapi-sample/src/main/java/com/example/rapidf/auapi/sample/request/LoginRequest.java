package com.example.rapidf.auapi.sample.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author lazycece
 */
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "參數username不能为空")
    private String username;
    @NotBlank(message = "參數password不能为空")
    private String password;
}
