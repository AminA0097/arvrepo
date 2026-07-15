package com.webapp.arvand.arvandback.Service;

import com.webapp.arvand.arvandback.Dto.LoginForm;
import com.webapp.arvand.arvandback.Dto.OptForm;
import com.webapp.arvand.arvandback.Entity.OptEntity;
import com.webapp.arvand.arvandback.Entity.RoleEntity;
import com.webapp.arvand.arvandback.Entity.UserEntity;
import com.webapp.arvand.arvandback.Guava.GuavaCache;
import com.webapp.arvand.arvandback.Guava.GuavaService;
import com.webapp.arvand.arvandback.Repo.OptRepo;
import com.webapp.arvand.arvandback.Repo.UserRepo;
import com.webapp.arvand.arvandback.Security.Jwt.JwtService;
import com.webapp.arvand.arvandback.Security.Auth.UserDetail;
import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Pattern;

import static com.webapp.arvand.arvandback.Utills.ApiErrorType.INVALID_PARAM;
import static com.webapp.arvand.arvandback.Utills.ApiErrorType.OPT_ERROR;

@Service
public class AuthService implements AuthInterface{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    public UserRepo userRepo;
    @Autowired
    public EntityManager entityManager;
    @Autowired
    private GuavaService guavaService;
    @Value("${opt.length}")
    private int optLength;
    @Value("${opt.expire}")
    private int optExpire;
    @Autowired
    private OptRepo optRepo;
    private static final Pattern OTP_PATTERN =
            Pattern.compile("^\\d{5}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^09\\d{9}$");
    @Override
    public boolean isAuthenticated() throws Exception {
        return false;
    }

    @Override
    public boolean logout(Authentication authentication) throws ApiException {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return guavaService.removeFromCache(userDetail.getUserId());
    }

    @Override
    public String login(LoginForm loginForm) throws ApiException {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getIdentifier(),
                            loginForm.getPassword()
                    ));
            
        }
        catch (BadCredentialsException e) {
            throw new ApiException(ApiErrorType.INVALID_CREDENTIALS,null);

        } catch (AuthenticationException e) {
            throw new ApiException(ApiErrorType.UNAUTHORIZED,null);
        }

        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        String jti = UUID.randomUUID().toString();
        String token = jwtService.generateToken(userDetail.getUserId(),jti);
//        if (token == null) {
//            throw new AuthException(AuthErrorType.INVALID_TOKEN, "Token generation failed");
//        }
        if(token != null){
            guavaService.removeFromCache(userDetail.getUserId());
            Collection roles = userDetail.getAuthorities();
            List<String> roleNames = userDetail.getAuthorities()
                    .stream()
                    .map(a -> a.getAuthority())
                    .toList();

            boolean added = guavaService.putToCache(
                    userDetail.getUserId(),
                    new GuavaCache(
                            userDetail.getUserId(),
                            userDetail.getUsername(),
                            jti,
                            roleNames
                    )
            );
            if(added){
                return token;
            }
        }
        return token;
    }

    @Override
    public String sendOpt(String phoneNumber,String type) throws ApiException {
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new ApiException(INVALID_PARAM,null);
        }
        String optCode =  generateOpt(phoneNumber);
        if(optCode == null){
            throw new ApiException(OPT_ERROR,null);
        }
        OptEntity optEntity = new OptEntity();
        optEntity.setUsed(false);
        optEntity.setPhoneNumber(phoneNumber);
        optEntity.setOptValue(optCode);
        optEntity.setType(type);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, optExpire);
        System.out.println("expire at" + calendar.getTime());
        optEntity.setExpireAt(calendar.getTime());
        try {
            optRepo.save(optEntity);
        }
        catch (Exception e){
            throw new ApiException(OPT_ERROR,null);
        }
        String text =  " کد برای " + phoneNumber + " ارسال شد. ";
        return text;
    }

    private String generateOpt(String phoneNumber) {
        try {
            optRepo.invalidateAllByPhoneNumber(phoneNumber);
        }
        catch (Exception e){
            return null;
        }
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < optLength; i++) {
            otp.append(random.nextInt(10));
        }
        System.out.println(otp);
        return otp.toString();
    }

    @Override
    public boolean verifyCode(OptForm optForm, String type) throws ApiException {
        if (!PHONE_PATTERN.matcher(optForm.getIdentifier()).matches()) {
            throw new ApiException(INVALID_PARAM,null);
        }
        if (!OTP_PATTERN.matcher(optForm.getOptCode()).matches()) {
            throw new ApiException(INVALID_PARAM,null);
        }
        boolean verified = false;
        try {
            Date now = new Date();

            Optional<OptEntity> otpOptional = optRepo.findValidOtp(
                    optForm.getIdentifier(),
                    optForm.getOptCode(),
                    type,
                    now
            );
            if(otpOptional.isPresent()){
                verified = true;
                OptEntity otp = otpOptional.get();
                otp.setUsed(true);
                optRepo.save(otp);
            }
        }
        catch (Exception e){
            return verified;
        }
        return verified;
    }

    @Override
    public String loginWithOpt(OptForm optForm, String type) throws ApiException {
        boolean verified = verifyCode(optForm, type);

        if (!verified) {
            throw new ApiException(ApiErrorType.OPT_VALIDATION_ERROR,null);
        }

        UserEntity entity = userRepo
                .findByPhoneNumberEqualsAndActiveTrue(optForm.getIdentifier())
                .orElseThrow(() ->
                        new ApiException(ApiErrorType.INVALID_CREDENTIALS,null)
                );

        String userId = entity.getId();

        String jti = UUID.randomUUID().toString();
        String token = jwtService.generateToken(userId, jti);

        if (token == null) {
            throw new ApiException(ApiErrorType.INVALID_CREDENTIALS,null);
        }

        List<String> roleNames = entity.getRoles()
                .stream()
                .map(RoleEntity::getRoleName)
                .toList();

        guavaService.removeFromCache(userId);

        boolean added = guavaService.putToCache(
                userId,
                new GuavaCache(
                        userId,
                        entity.getUserName(),
                        jti,
                        roleNames
                )
        );

        if (!added) {
            throw new ApiException(ApiErrorType.SERVER_ERROR,null);
        }

        return token;
    }
}
