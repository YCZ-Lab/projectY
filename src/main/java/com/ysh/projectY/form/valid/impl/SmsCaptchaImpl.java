package com.ysh.projectY.form.valid.impl;

import com.ysh.projectY.service.SmsCaptchaService;
import com.ysh.projectY.form.valid.SmsCaptcha;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class SmsCaptchaImpl implements ConstraintValidator<SmsCaptcha, com.ysh.projectY.form.SmsCaptcha> {

    @Autowired
    SmsCaptchaService smsCaptchaService;

    // register: 注册时需要短信验证码
    // login: 支持手机号/短信验证码登录
    private final String[] ALL_SOURCE = {"register", "login"};

    @Override
    public boolean isValid(com.ysh.projectY.form.SmsCaptcha smsCaptcha, ConstraintValidatorContext context) {
        // 关闭默认消息
        context.disableDefaultConstraintViolation();
        if (!Arrays.asList(ALL_SOURCE).contains(smsCaptcha.getSource())) {
            context.buildConstraintViolationWithTemplate("projectY.valid.SmsCaptcha.source.notExist").addConstraintViolation();
            return false;
        }

        return true;
    }
}

