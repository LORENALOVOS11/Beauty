package com.beautysalon.BeautySalon;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarPassword {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = encoder.encode("123456");

        System.out.println("CONTRASEÑA CIFRADA:");
        System.out.println(password);
    }
}