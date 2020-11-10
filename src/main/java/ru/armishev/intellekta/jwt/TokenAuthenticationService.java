package ru.armishev.intellekta.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.armishev.intellekta.security.SecurityUserDetailsManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

public class TokenAuthenticationService {
    private static final String SECRET = "Secret";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    private static Logger LOG = LoggerFactory.getLogger(TokenAuthenticationService.class);

    static void addAuthentication(HttpServletResponse response, String username) {
        response.addHeader(HEADER_STRING, TOKEN_PREFIX+" "+generateToken(username));
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = getToken(request);

        if (!hasText(token)) {
            return null;
        }

        /* Проверка наличия сессии по токену */
        String userName = getUserName(token);

        UserDetails user = new SecurityUserDetailsManager().loadUserByUsername("user");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        return authentication;
    }

    private static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private static String getToken(HttpServletRequest request) {
        if (request.getHeader(HEADER_STRING) != null) {
            return request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX+" ", "");
        } else {
            return null;
        }
    }

    public static String getUserName(String token) {
        try {
            return token != null ? Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject() : null;
        } catch (ExpiredJwtException e) {
            LOG.info("Ошибка обработки токена - токен просрочен {}", token);
            return null;
        } catch (JwtException e) {
            LOG.info("Ошибка обработки токена {}", token);
            return null;
        }
    }
}
