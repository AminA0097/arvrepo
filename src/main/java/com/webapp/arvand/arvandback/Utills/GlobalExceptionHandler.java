package com.webapp.arvand.arvandback.Utills;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Value("${app.develop:false}")
    private boolean develop;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleAuth(ApiException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        if(develop){
            body.put("message", ex.getCmsg());
        }
        else{
            body.put("message", ex.getMessage());
        }
        body.put("code", ex.getType().name());

        return ResponseEntity
                .status(ex.getStatus())
                .body(body);
    }
}
