package com.demcare.demo.security;

import com.demcare.demo.config.PasswordEncoderBean;
import com.demcare.demo.entities.User;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class DemcareAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoderBean passwordEncoderBean;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String mail = authentication.getName();
        String passwordWithoutEncode = authentication.getCredentials().toString();

        User user = userService.findByMail(mail);

        String passwordEncoded = user.getPassword();

        PasswordEncoder passwordEncoder = passwordEncoderBean.encoder();

        if(passwordEncoder.matches(passwordWithoutEncode, passwordEncoded)) {
            //LOGIN OK, update lastLogin
            Long now = new Date().getTime();
            userService.update(user);

            return new UsernamePasswordAuthenticationToken(
                    mail, passwordWithoutEncode, new ArrayList<>());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
