package aero.icarus2020.keypairadministrator.rest.security;

import aero.icarus2020.keypairadministrator.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenAuthenticationService {

    public static final String AUTH_HEADER_NAME = "Authorization";

    private static final String TOKEN_TYPE = "Bearer ";

    private final TokenHandler tokenHandler;
    private static final Logger logger = LogManager.getLogger(TokenAuthenticationService.class);

    @Autowired
    public TokenAuthenticationService(
            @Value("${token.signer.secret}") String secret) {
        tokenHandler = new TokenHandler(secret);
    }

    public void addAuthentication(HttpServletResponse response, UserDetails authenticatedUser) {

        final String cookieName = "auth_token";
        final String cookieValue =
                tokenHandler.createTokenForUser(
                        new User(authenticatedUser.getUsername(), Util.createAlgorithm("vsauhsdavuhasiudvh", Util.ALGORITHM.SHA.name()), authenticatedUser.getAuthorities()));
        final Boolean useSecureCookie = false;
        final int expiryTime = 1000 * 60 * 60 * 24 * 10;
        final String cookiePath = "/";

        Cookie cookie = new Cookie(cookieName, cookieValue);

        cookie.setSecure(useSecureCookie);

        cookie.setMaxAge(expiryTime);

        cookie.setPath(cookiePath);

        response.addCookie(cookie);

        response.addHeader(
                AUTH_HEADER_NAME,
                TOKEN_TYPE.concat(
                        tokenHandler.createTokenForUser(
                                new User(
                                        authenticatedUser.getUsername(),
                                        Util.createAlgorithm("AtBzjOcXeSAI6hqiT$z$jFI7#", Util.ALGORITHM.SHA.name()), authenticatedUser.getAuthorities()))));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TokenAuthenticationService.AUTH_HEADER_NAME);
        // If token is invalid return null
        if (token == null || !token.startsWith(TOKEN_TYPE)) {
            Cookie cookie = null;
            Cookie[] cookies = request.getCookies();
            List<Cookie> cookieList = null;

            if (null != cookies) {
                cookieList =
                        Arrays.stream(cookies)
                                .filter(c -> c.getName().equals("auth_token"))
                                .collect(Collectors.toList());
                cookie = null != cookieList && !cookieList.isEmpty() ? cookieList.get(0) : null;
                if (null != cookie) {
                    return tokenHandler.parseUserFromToken("Bearer " + cookie.getValue(), request);
                }
            }

            return null;
        }

        // Return user authentication
        return tokenHandler.parseUserFromToken(token, request);
    }

    public Authentication getAuthenticationForWS(ServerHttpRequest request) {

        String token = null;

        for (Map.Entry<String, List<String>> header : request.getHeaders().entrySet()) {

            if (header.getKey().equals("cookie")) {

                for (String tempValue : header.getValue()) {

                    if (tempValue.contains("auth_token")) {

                        for (String tempSTR : tempValue.split("\\;")) {

                            if (tempSTR.contains("auth_token")) {

                                token = tempSTR.split("\\=")[1].trim();
                            }
                        }
                    }
                }
            }
        }

        // If token is invalid return null
        if (null != token && !token.isEmpty()) {

            return tokenHandler.parseUserFromToken("Bearer " + token, null);

        } else {
            return null;
        }
    }
}
