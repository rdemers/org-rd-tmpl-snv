package org.rd.tmpl.snv.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;

import org.rd.tmpl.snv.dto.ERole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    final private String CST_SECRET;
    @SuppressWarnings("unused")
	final private int    CST_EXPIRATION;
    final private String CST_AUTHORITIES;

    protected JwtUtils() { // Ne devrait pas être utilisé. Usage interne, tests unitaires.
        super();
        this.CST_SECRET = null;
        this.CST_EXPIRATION = -1;
        this.CST_AUTHORITIES = null;
    }

    public JwtUtils(@Value("${org.rd.tmpl.snv.secret}") String secret,
                    @Value("${org.rd.tmpl.snv.expiration}") int expiration,
                    @Value("${org.rd.tmpl.snv.authorities}") String authorities) {
        super();
        this.CST_SECRET = secret;
        this.CST_EXPIRATION = expiration;
        this.CST_AUTHORITIES = authorities;
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String authorities            = authentication.getAuthorities()
                                                      .stream()
                                                      .map(GrantedAuthority::getAuthority)
                                                      .collect(Collectors.joining(","));

        return Jwts.builder()
                   .setId(UUID.randomUUID().toString())
                   .setSubject(userPrincipal.getUsername())
                   .claim(CST_AUTHORITIES, authorities)
                   .setIssuedAt(new Date())
                   //.setExpiration(new Date((new Date()).getTime() + CST_EXPIRATION))
                   // An API/KEY does not expire. Add an expiration for testing. Att: update expiration.
                   .signWith(SignatureAlgorithm.HS256, CST_SECRET) // Should be an asymmetric key algorithm.
                   .compact();                                     // Demonstration only.
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(CST_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public List<ERole> getAuthoritiesFromJwtToken(String token) {

        List<ERole> roles = new ArrayList<ERole>();
        String authorities = Jwts.parser().setSigningKey(CST_SECRET).parseClaimsJws(token).getBody().get(CST_AUTHORITIES, String.class);
        if (authorities != null && ! authorities.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(authorities, ",");
            while (tokenizer.hasMoreElements()) {
                roles.add(ERole.valueOf(tokenizer.nextToken()));
            }
        }
        return roles;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(CST_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("JWT token is expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("JWT token is unsupported: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty: {}", ex.getMessage());
        }
        return false;
    }

    public String decodeJwtToken(String jwtToken) {
        Jws<Claims> jws;
        try {
            jws = Jwts.parser().setSigningKey(CST_SECRET).parseClaimsJws(jwtToken);
        }
        catch (JwtException ex) {
            logger.error("Invalid JWT : {}", ex.getMessage());
            return "";
        }
        return jws.getBody().toString();
    }
}