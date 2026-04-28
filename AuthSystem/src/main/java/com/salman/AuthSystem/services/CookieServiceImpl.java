package com.salman.AuthSystem.services;

import com.salman.AuthSystem.interfaces.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
public class CookieServiceImpl implements CookieService {
    private final String refreshTokenCookieName;
    private final boolean cookieHttpOnly;
    private final boolean cookieSecure;
    //    private final int cookieMaxAge;
    private final String cookieDomain;
    private final String cookieSameSite;

    public CookieServiceImpl(@Value(
                                     "${security.jwt.refresh-token-cookie-name}"
                             ) String refreshTokenCookieName,
                             @Value("${security.jwt.cookie-http-only}") boolean cookieHttpOnly,
                             @Value("${security.jwt.cookie-secure}") boolean cookieSecure,
                             @Value("${security.jwt.cookie-domain}") String cookieDomain,
                             @Value("${security.jwt.cookie-same-site}") String cookieSameSite) {
        this.refreshTokenCookieName = refreshTokenCookieName;
        this.cookieHttpOnly = cookieHttpOnly;
        this.cookieSecure = cookieSecure;

        this.cookieDomain = cookieDomain;
        this.cookieSameSite = cookieSameSite;
    }

//    public String getRefreshToken

    @Override
    public void attachRefreshTokenCookie(HttpServletResponse response, String value, int maxAge) {
        ResponseCookie.ResponseCookieBuilder responseCookieBuilder = ResponseCookie.from(refreshTokenCookieName).value(value)
                .httpOnly(cookieHttpOnly)
                .secure(cookieSecure)
                .path("/")

                .maxAge(maxAge)
                .sameSite(cookieSameSite);

        if (cookieDomain != null && !cookieDomain.isBlank()) {
            responseCookieBuilder.domain(cookieDomain);
        }

        ResponseCookie responseCookie = responseCookieBuilder.build();

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

    }


    @Override
    public void clearRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie.ResponseCookieBuilder responseCookieBuilder = ResponseCookie.from(refreshTokenCookieName).value("").maxAge(0).httpOnly(cookieHttpOnly).path("/").sameSite(cookieSameSite).secure(cookieSecure);

        if (cookieDomain != null && !cookieDomain.isBlank()) {
            responseCookieBuilder.domain(cookieDomain);
        }

        ResponseCookie responseCookie = responseCookieBuilder.build();

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

    }

    @Override
    public void addNoStoreHeader(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
    }


}
