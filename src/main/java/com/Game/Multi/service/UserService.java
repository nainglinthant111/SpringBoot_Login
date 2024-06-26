package com.Game.Multi.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Game.Multi.model.User;
import com.Game.Multi.reporisity.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*-=+|;:'\",.<>?/";

    public static String generatePassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        String allCharacters = UPPERCASE_CHARACTERS + LOWERCASE_CHARACTERS + DIGITS + SPECIAL_CHARACTERS;
        password.append(getRandomChar(UPPERCASE_CHARACTERS, random));
        password.append(getRandomChar(LOWERCASE_CHARACTERS, random));
        password.append(getRandomChar(DIGITS, random));
        password.append(getRandomChar(SPECIAL_CHARACTERS, random));
        for (int i = 4; i < length; i++) {
            password.append(getRandomChar(allCharacters, random));
        }

        String shuffledPassword = shuffle(password.toString(), random);

        return shuffledPassword;
    }

    private static char getRandomChar(String characters, SecureRandom random) {
        int randomIndex = random.nextInt(characters.length());
        return characters.charAt(randomIndex);
    }

    private static String shuffle(String input, SecureRandom random) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }

    public String decodePass(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(password.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b & 0xff));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String uncodePass(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(password.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b & 0xff));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String saveUser(User user) {
        userRepo.save(user);
        return null;
    }

    public String loginUser(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            return userOptional.toString();
        } else {
            return "NG";
        }

    }
}
