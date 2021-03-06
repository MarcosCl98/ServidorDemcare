package com.demcare.demo.validators;

import com.demcare.demo.entities.User;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SingUpFormValidator implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Error.empty");

        if(user.getRole().equals("ROLE_INSTITUCION")){
            if (userService.findByName(user.getName()) != null) {
                errors.rejectValue("name", "Error.signup.email.duplicate");
            }
        }
        if(userService.findByUsername(user.getUsername())!=null){
            errors.rejectValue("username", "Error.signup.email.duplicate");
        }
        if (user.getName().length() < 5 || user.getName().length() > 24) {
            errors.rejectValue("name", "Error.signup.name.length");
        }
        if (user.getSurname().length() < 5 || user.getSurname().length() > 24) {
            errors.rejectValue("surname", "Error.signup.surname.length");
        }
        if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
            errors.rejectValue("password", "Error.signup.password.length");
        }
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm",
                    "Error.signup.passwordConfirm.coincidence");
        }
    }


}