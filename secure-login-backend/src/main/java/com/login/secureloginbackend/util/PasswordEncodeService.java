package com.login.secureloginbackend.util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;



public class PasswordEncodeService {

    @Getter
    private static final PasswordEncodeService instance = new PasswordEncodeService();

    private PasswordEncodeService() {
        // private constructor to prevent instantiation
    }


    public static String encodePassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //TODO probar con la cantidad recomendad de iteraciones:1,300,000
        int iterations = 1300000;
        char[] chars = password.toCharArray();
        byte[] salt = genSalt();
        //PBKDF2WithHmacSHA1 algoritmo de encriptacion
        //PBEKeySpec especificaciones para la encriptacion, se le pasa la contraseña, el salt, la cantidad de iteraciones y el tamaño de la llave
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        //se crea una instancia de la clase SecretKeyFactory que se encarga de generar la llave
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        //se genera la llave
        byte[] hash = skf.generateSecret(spec).getEncoded();


        return iterations+ ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] genSalt() throws NoSuchAlgorithmException {
        // SHA1PRNG nombre del algoritmo RNG (Random Number Generator) que genera el secureRandom
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        //llena el arreglo salt con bytes aleatorios generados por el secureRandom
        secureRandom.nextBytes(salt);

        return salt;
    }

    private static String toHex(byte[] bytes) {
        //convierte un arreglo de bytes a un numero hexadecimal positivo
        BigInteger bi = new BigInteger(1, bytes);
        //convierte el numero hexadecimal a string
        String hex = bi.toString(16);
        //se calcula la cantidad de ceros que se deben agregar para que el numero hexadecimal tenga 32 caracteres
        //Es decir, miramos si tiene 32 caracteres, si no tiene, se agregan ceros a la izquierda
        int paddingLength = (bytes.length * 2) - hex.length();
        //se agregan los ceros
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static boolean passwordVerify(String password, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //se separa el storedPassword en sus componentes
        String[] parts = storedPassword.split(":");
        //se obtiene el numero de iteraciones
        int iterations = Integer.parseInt(parts[0]);
        //se obtiene el salt
        byte[] salt = fromHex(parts[1]);
        //se obtiene el hash
        byte[] hash = fromHex(parts[2]);

        //se crea una instancia de la clase PBEKeySpec que se encarga de generar la llave
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8);
        //se crea una instancia de la clase SecretKeyFactory que se encarga de generar la llave
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        //se genera la llave
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        //se comparan los hash
        //esto es un XOR bitwise
        int diff = hash.length ^ testHash.length;

        //se recorre el arreglo de bytes y se hace un XOR bitwise
        //esto se hace con el fin de protegernos contra ataques de timing
        for(int i = 0; i < hash.length && i < testHash.length; i++){
            diff |= hash[i] ^ testHash[i];
        }

        //si los hash son iguales, diff es 0
        return diff == 0;

    }

    private static byte[] fromHex(String hex) {
        //convierte un numero hexadecimal a un arreglo de bytes
        byte[] bytes = new byte[hex.length() / 2];
        //se recorre el string hex y se convierte cada par de caracteres a un byte
        for (int i = 0; i < bytes.length; i++) {
            //se obtiene el par de caracteres
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        //se retorna el arreglo de bytes
        return bytes;
    }
}
