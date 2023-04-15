package com.lazycece.rapidf.auapi;

import com.lazycece.au.api.token.Subject;

/**
 * @author lazycece
 */
public class UserSubject implements Subject {

    private String userId;
    private String username;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
