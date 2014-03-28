package com.ascense.flexdo.core;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;


public class PasswordHandler {
    private MessageDigest digest;
    private SecureRandom random;

    public PasswordHandler() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
            random = new SecureRandom();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported!");
        }
    }

    public boolean verifyPassword(String pass, String salt, String hash) {
        try {
            if (Arrays.equals(
                    generateHash(pass.getBytes(), fromHexString(salt)),
                    fromHexString(hash))) {
                return true;
            }
        } catch (NumberFormatException e) {}
        return false;
    }

    public byte[] generateHash(byte[] pass, byte[] salt) {
        digest.reset();
        digest.update(pass);
        digest.update(salt);
        return digest.digest();
    }

    public byte[] generateSalt() {
        byte salt[] = new byte[32];
        random.nextBytes(salt);
        return salt;
    }

    public static String toHexString(byte[] arr) {
        String str = "";
        for (int i = 0; i < arr.length; ++i) {
            str += String.format("%2x", arr[i]).replace(' ', '0');
        }

        if (str.startsWith("00")) {
            str = str.substring(2);
        }

        return str;
    }

    public static byte[] fromHexString(String str) {
        BigInteger b = new BigInteger(str, 16);
        return b.toByteArray();
    }
}
