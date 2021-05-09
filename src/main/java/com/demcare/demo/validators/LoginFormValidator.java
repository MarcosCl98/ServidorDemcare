package com.demcare.demo.validators;

import com.demcare.demo.entities.User;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginFormValidator implements Validator {
    @Autowired
    private UserService usersService;
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (user.isSuspend()) {
            errors.rejectValue("mail", "Error.login.suspend");
        }
    }


}