package com.ysh.projectY.form.valid.impl;

import com.ysh.projectY.form.valid.CreateUser;
import com.ysh.projectY.service.UserDetailService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateUserImpl implements ConstraintValidator<CreateUser, com.ysh.projectY.form.CreateUser> {

    UserDetailService userDetailService;

    public CreateUserImpl(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public boolean isValid(com.ysh.projectY.form.CreateUser createUser, ConstraintValidatorContext context) {
        // 关闭默认消息
        context.disableDefaultConstraintViolation();
        if (userDetailService.findByUsername(createUser.getUsername()).isPresent()) {
            context.buildConstraintViolationWithTemplate("projectY.valid.CreateUser.username.exist").addConstraintViolation();
            return false;
        }

        if (userDetailService.findByMobilePhone(createUser.getMobilePhone()).isPresent()) {
            context.buildConstraintViolationWithTemplate("projectY.valid.CreateUser.mobilePhone.exist").addConstraintViolation();
            return false;
        }

        if (createUser.getNickname() == null || "".equals(createUser.getNickname())) {
            createUser.setNickname("");
        }

        if (createUser.getAvatarURL() == null || "".equals(createUser.getAvatarURL())) {
            createUser.setAvatarURL("http://front.ycz.com:8000/logo.png");
        }
        return true;
    }
}

