package com.webapp.arvand.arvandback.Utills;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class PemUtils {

    @Value("classpath:keys/private_key.pem")
    private Resource privateKeyResource;

    @Value("classpath:keys/public_key.pem")
    private Resource publicKeyResource;

    public PrivateKey getPrivateKey() {
        try {
            String key = new String(privateKeyResource.getInputStream().readAllBytes())
                    .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePrivate(spec);

        } catch (Exception e) {
            throw new RuntimeException("Error loading private key", e);
        }
    }

    public PublicKey getPublicKey() {
        try {
            String key = new String(publicKeyResource.getInputStream().readAllBytes())
                    .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);

            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePublic(spec);

        } catch (Exception e) {
            throw new RuntimeException("Error loading public key", e);
        }
    }
}
