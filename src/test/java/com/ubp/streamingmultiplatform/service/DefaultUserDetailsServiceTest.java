package com.ubp.streamingmultiplatform.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DefaultUserDetailsServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void decodePassword() {
        System.out.println(passwordEncoder.encode("Matrox"));
    }
}