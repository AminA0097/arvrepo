package com.webapp.arvand.arvandback.Service;

import com.webapp.arvand.arvandback.Dto.LoginForm;
import com.webapp.arvand.arvandback.Dto.OptForm;
import com.webapp.arvand.arvandback.Utills.ApiException;
import org.springframework.security.core.Authentication;

public interface AuthInterface {
    public boolean isAuthenticated()throws Exception;
    public boolean logout(Authentication authentication)throws ApiException;
    public String login(LoginForm loginForm)throws Exception;

    public String sendOpt(String phoneNumber,String type)throws ApiException;

    public boolean verifyCode(OptForm optForm, String type)throws ApiException;

    public String loginWithOpt(OptForm optForm, String type)throws ApiException;
}
