
package com.beautysalon.BeautySalon;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GenerarPassword {

    public static void main(String[] args) {

        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String password = "123456789!";
        String passwordCifrada = encoder.encode(password);

        System.out.println("CONTRASEÑA CIFRADA:");
        System.out.println(passwordCifrada);
    }
}
