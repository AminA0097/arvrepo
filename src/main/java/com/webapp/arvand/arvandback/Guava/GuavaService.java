package com.webapp.arvand.arvandback.Guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class GuavaService {
    private final Cache<String,GuavaCache> authCache =
            CacheBuilder.newBuilder()
                    .maximumSize(500)
                    .expireAfterWrite(
                            16,
                            TimeUnit.MINUTES
                    )
                    .build();

    public boolean putToCache(String ui, GuavaCache cache) {
        try {
            authCache.put(ui, cache);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean removeFromCache(String ui) {
        try {
            authCache.invalidate(ui);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public GuavaCache checkExistAncCompare(Claims claims) {
        String id = claims.getSubject();
        String jit = claims.getId();
        GuavaCache guavaCache = authCache.getIfPresent(id);
        if (guavaCache == null) {
            return null;
        }
        if(!guavaCache.getCurrentJti().equals(jit)) {
            return null;
        }
        return guavaCache;
    }
}
