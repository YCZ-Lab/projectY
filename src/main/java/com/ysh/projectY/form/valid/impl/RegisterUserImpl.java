package com.ysh.projectY.form.valid.impl;

import com.ysh.projectY.service.UserDetailService;
import com.ysh.projectY.form.valid.RegisterUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegisterUserImpl implements ConstraintValidator<RegisterUser, com.ysh.projectY.form.RegisterUser> {

    UserDetailService userDetailService;

    public RegisterUserImpl(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public boolean isValid(com.ysh.projectY.form.RegisterUser registerUser, ConstraintValidatorContext context) {
        // 关闭默认消息
        context.disableDefaultConstraintViolation();
        if (userDetailService.findByUsername(registerUser.getUsername()).isPresent()) {
            context.buildConstraintViolationWithTemplate("projectY.valid.RegisterUser.username.exist").addConstraintViolation();
            return false;
        }

        if (userDetailService.findByMobilePhone(registerUser.getMobilePhone()).isPresent()) {
            context.buildConstraintViolationWithTemplate("projectY.valid.RegisterUser.mobilePhone.exist").addConstraintViolation();
            return false;
        }

        if (registerUser.getNickname() == null || "".equals(registerUser.getNickname())) {
            registerUser.setNickname("");
        }

        if (registerUser.getAvatarURL() == null || "".equals(registerUser.getAvatarURL())) {
            registerUser.setAvatarURL("http://front.ycz.com:8000/logo.png");
        }

        return true;
    }
}

