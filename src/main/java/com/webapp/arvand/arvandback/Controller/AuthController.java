package com.webapp.arvand.arvandback.Controller;

import com.webapp.arvand.arvandback.Service.AuthService;
import com.webapp.arvand.arvandback.Dto.LoginForm;
import com.webapp.arvand.arvandback.Dto.OptForm;
import com.webapp.arvand.arvandback.Dto.UserDto;
import com.webapp.arvand.arvandback.Security.Auth.UserDetail;
import com.webapp.arvand.arvandback.Utills.ApiResponse;
import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    private static String optTypeLogin = "6060";
    private static String optTypeSighUp = "6061";

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(Authentication authentication) throws ApiException {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new ApiException(ApiErrorType.INVALID_CREDENTIALS,null);
        }
        if(!authService.logout(authentication)){
            throw new ApiException(ApiErrorType.INVALID_CREDENTIALS,null);
        };
        ResponseCookie cookie = ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success("خروج موفقیت آمیز بود"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody LoginForm loginForm)
            throws ApiException {

        String token = authService.login(loginForm);

//        if (token == null) {
//            throw new ApiException(ApiErrorType.INVALID_CREDENTIALS);
//        }

        ResponseCookie cookie = ResponseCookie.from("auth_token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofHours(2))
                .sameSite("Lax")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success(null));
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<?> me(
            Authentication authentication
    )
            throws ApiException {
        if (authentication == null) {
            throw new ApiException(ApiErrorType.FORBIDDEN,null);
        }
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();


        UserDto dto = new UserDto(
                userDetail.getUserId(),
                userDetail.getUsername()
        );
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(dto));
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<ApiResponse<Void>> sendOtp(@RequestBody OptForm optForm) throws ApiException {

        String text = authService.sendOpt(optForm.getIdentifier(), optTypeLogin);
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(text));
    }

    @PostMapping("/verifyOptLogin")
    public ResponseEntity<ApiResponse<Void>> verifyCode(@RequestBody OptForm optForm) throws ApiException {
        String token = authService.loginWithOpt(optForm, optTypeLogin);
        ResponseCookie cookie = ResponseCookie.from("auth_token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofHours(2))
                .sameSite("Lax")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success("خوش آمدید"));
    }
}
