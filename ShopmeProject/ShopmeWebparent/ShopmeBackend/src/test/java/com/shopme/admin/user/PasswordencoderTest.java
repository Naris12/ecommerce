package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordencoderTest {

    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String rawpassworf="ris2020";
        String encodedpassword=passwordEncoder.encode(rawpassworf);
        System.out.println(encodedpassword);
        boolean matches = passwordEncoder.matches(rawpassworf, encodedpassword);
        assertThat(matches).isTrue();
    }
}
