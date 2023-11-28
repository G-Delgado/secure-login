package com.login.secureloginbackend.service.security;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    /**
     * Genera un token con los claims por defecto
     * @param user - Usuario al que se le va a generar el token
     * @return - Token generado
     */
    public String generateToken(UserDetails user){
        return generateToken(new HashMap<>(),user);
    }

    /**
     * Genera un token con los claims que se le pasen por parametro
     * @param claims - Claims que se quieren agregar al token
     * @param user - Usuario al que se le va a generar el token
     * @return - Token generado
     */
    private String generateToken(HashMap<String,Object> claims, UserDetails user){
        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        return Jwts
                .builder()
                .setClaims(claims)
                .claim("scope",scope)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        System.out.println("Longitud de la clave: " + keyBytes.length * 8 + " bits");
        System.out.println("Bytes de la clave: " + Arrays.toString(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene el token de un request
     * @param request - Request del que se quiere obtener el token
     * @return - Token del request
     */
    public String getTokenFromRequest(HttpServletRequest request){
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }

    /**
     * Obtiene el username de un token
     * @param token - Token del que se quiere obtener el username
     * @return - Username del token
     */
    public String extractUsername(String token) {
        return getClaim(token,Claims::getSubject);
    }

    /**
     * Valida si un token es válido para un usuario
     * @param token - Token que se quiere validar
     * @param userDetails - Usuario al que se le quiere validar el token
     * @return - True si el token es válido para el usuario, false de lo contrario
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Obtiene todos los claims de un token
     * @param token - Token del que se quieren obtener los claims
     * @return - Claims del token
     */
    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        //System.out.println(claims.get("scope"));
        return claimsResolver.apply(claims);
    }

    public <T> Object getRole (String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        //System.out.println(claims.get("scope"));
        return claims.get("scope");
    }

    public Date extractExpiration(String token){
        return getClaim(token,Claims::getExpiration);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

}

