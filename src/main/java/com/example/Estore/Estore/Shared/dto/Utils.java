package com.example.Estore.Estore.Shared.dto;

import com.example.Estore.Estore.Security.SecurityConstants;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.util.Date;

/**
 * This class is provides with some basic methods to generate some unique Ids.
 */
@Component
public class Utils {

    private final SecureRandom RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Method is used to generate unique userId.
     * @param lenght length of userId to be generated.
     * @return String.
     */
    public String generateUserId(int lenght) {
        return generateRandomString(lenght);
    }

    /**
     * Method is used to generate unique addressId.
     * @param lenght length of addressId to be generated.
     * @return String.
     */
    public String generateAddressId(int lenght) {
        return generateRandomString(lenght);
    }

    /**
     * Method is used to generate unique cardId.
     * @param lenght length of cardId to be generated.
     * @return String.
     */
    public String generateCardId(int lenght) {
        return generateRandomString(lenght);
    }

    /**
     * Method is used to generate a Jwt token for email verification.
     * @param userId userId to be used as subject.
     * @return String.
     */
    public String generateEmailVerificationToken(String userId){
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants.EMAIL_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.getTokenSecret())
                .compact();
        return token;
    }

    /**
     * Method is used to check whether the token has expired.
     * @param token token.
     * @return boolean.
     */
    public static boolean hasTokenExpired(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token).getBody();
            Date tokenExpirationTime = claims.getExpiration();
            Date currentTime = new Date();
            System.out.println(tokenExpirationTime.before(currentTime));
            return tokenExpirationTime.before(currentTime);
        }
        catch (ExpiredJwtException ex) {
            return true;
        }
    }

    /**
     * Method is used to generate jwt token for resetting the password.
     * @param userId userId to be used as subject.
     * @return String.
     */
    public String generatePasswordResetToken(String userId){
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EMAIL_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.getTokenSecret())
                .compact();
        return token;
    }

    /**
     * Method is used to generate a random string to be used by other methods in this class.
     * @param lenght length of the string to be generated.
     * @return String.
     */
    public String generateRandomString(int lenght) {
        StringBuilder returnValue = new StringBuilder(lenght);

        for (int i = 0; i < lenght; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

}