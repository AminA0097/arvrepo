package com.webapp.arvand.arvandback.Guava;
import lombok.Getter;

import java.util.List;


public final class GuavaCache {

    private final String userId;
    private final String username;
    private final String currentJti;

    private final List<String> roles;

    public GuavaCache(String userId,
                      String username,
                      String currentJti,
                      List<String> roles) {

        this.userId = userId;
        this.username = username;
        this.currentJti = currentJti;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getCurrentJti() {
        return currentJti;
    }

    public List<String> getRoles() {
        return roles;
    }
}