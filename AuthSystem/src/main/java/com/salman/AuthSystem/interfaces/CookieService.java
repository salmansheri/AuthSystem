package com.salman.AuthSystem.interfaces;

import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void attachRefreshTokenCookie(HttpServletResponse response, String value, int maxAge);
     void clearRefreshTokenCookie(HttpServletResponse response);
    void addNoStoreHeader(HttpServletResponse response);

}
