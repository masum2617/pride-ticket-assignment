package com.pridesys.ticketing.filters;


import com.pridesys.ticketing.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Masum Hasan
 * To Generate Token after login
 * <p>
 * /*I don't want to generate multiple jwt token for each request
 */
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        /*once the initial login the complete we want to generate the jwt token, for future request itwill not generate any new token*/

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null != authentication) {

            /*To read the secret value from the envitonment*/
            Environment env = getEnvironment();
            if (null != env) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                /*this issuer helps to indentify which org/or person issued this jwt token*/
                String jwt = Jwts.builder().issuer("Ticket System").subject("JWT Token")
                        .claim("username", authentication.getName())
                        .claim("authorities", authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
//                        .expiration(new Date((new Date()).getTime() + 2 * 60 * 1000))
                        .expiration(new Date((new Date()).getTime() + 30000000)) // for around 8 hours
                        .signWith(secretKey).compact(); // create that digital signature inside our jwt token
                response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
            }
            /*Use the secret value, the value is needed for token generation*/

        }

        filterChain.doFilter(request, response);

    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/user/login"); //false when the filter will executed
    }

}
