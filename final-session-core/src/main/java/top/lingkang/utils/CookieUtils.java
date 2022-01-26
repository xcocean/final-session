package top.lingkang.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class CookieUtils {
    public static String getCookieValue(String name, Cookie[] cookies) {
        if (cookies == null)
            return null;
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void addSessionIdToCookie(String name, String value, boolean addAge, long time, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (addAge)
            cookie.setMaxAge((int) (time / 1000));
        response.addCookie(cookie);
    }
}
