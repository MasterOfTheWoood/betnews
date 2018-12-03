package ru.betnews.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.betnews.entity.security.BetNewsUserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 14.04.2018.
 */
@Component
public class TokenUtils {

    private final String AUDIENCE_UNKNOWN = "unknown";
    private final String AUDIENCE_WEB = "web";
    private final String AUDIENCE_MOBILE = "mobile";
    private final String AUDIENCE_TABLET = "tablet";

    private String secret = "ffwe$f3q4iwxc@";

    private Long expiration = 604800L;

    public String getUsernameFromToken(String token){
        String username;
        try{
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token){
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get("created"));
        }catch (Exception e){
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token){
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        }catch (Exception e){
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token){
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get("audience");
        }catch (Exception e){
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            claims = null;
        }
        return claims;
    }

    private Date generateCurrentDate(){
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private  Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(generateCurrentDate());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset){
        return (lastPasswordReset != null) && created.before(lastPasswordReset);
    }

    private Boolean ignoreTokenExpiration(String token){
        String audience = getAudienceFromToken(token);
        return AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("audience", "web");
        claims.put("created", generateCurrentDate());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset){
        final Date created = getCreatedDateFromToken(token);
        return (!(isCreatedBeforeLastPasswordReset(created, lastPasswordReset))&&(!(isTokenExpired(token) ||
         ignoreTokenExpiration(token))));
    }

    public String refreshToken(String token){
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put("created", generateCurrentDate());
            refreshedToken = generateToken(claims);
        }catch (Exception e){
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        BetNewsUserDetails user = (BetNewsUserDetails) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        return username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset());
    }
}
