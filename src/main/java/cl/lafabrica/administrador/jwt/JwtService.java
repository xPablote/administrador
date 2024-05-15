package cl.lafabrica.administrador.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String KEY_SECRET = "123456789";

    public String getToken(UserDetails usuario) {
        return getToken(new HashMap<>(),usuario);
    }

    private String getToken(Map<String,Object> extraClaims, UserDetails usuario) {

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getToken(), SignatureAlgorithm.ES256)
                .compact();
    }

    private Key getToken() {
        byte[] keyBytes = Decoders.BASE64URL.decode(KEY_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
