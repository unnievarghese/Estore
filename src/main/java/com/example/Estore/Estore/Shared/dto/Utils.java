package com.example.Estore.Estore.Shared.dto;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class Utils {

    private final SecureRandom RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateUserId(int lenght) {
        return generateRandomString(lenght);
    }

    public String generateAddressId(int lenght) {
        return generateRandomString(lenght);
    }

    public String generateCardId(int lenght) {
        return generateRandomString(lenght);
    }





    public String generateRandomString(int lenght) {
        StringBuilder returnValue = new StringBuilder(lenght);

        for (int i = 0; i < lenght; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
}