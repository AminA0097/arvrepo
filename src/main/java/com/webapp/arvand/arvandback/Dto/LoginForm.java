package com.webapp.arvand.arvandback.Dto;
import com.webapp.arvand.arvandback.Annotaion.ValidPassword;
import jakarta.validation.constraints.*;


public class LoginForm {
    @NotBlank
    private String identifier;

    @NotBlank
    @ValidPassword
    private String password;

    private boolean rememberMe;

    public @NotBlank String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(@NotBlank String identifier) {
        this.identifier = identifier;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
