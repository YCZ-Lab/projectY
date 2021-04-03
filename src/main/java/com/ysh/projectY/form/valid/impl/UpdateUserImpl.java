package com.ysh.projectY.form.valid.impl;

import com.ysh.projectY.entity.User;
import com.ysh.projectY.form.valid.UpdateUser;
import com.ysh.projectY.service.UserDetailService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.TimeZone;

public class UpdateUserImpl implements ConstraintValidator<UpdateUser, com.ysh.projectY.form.UpdateUser> {

    UserDetailService userDetailService;

    public UpdateUserImpl(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public boolean isValid(com.ysh.projectY.form.UpdateUser updateUser, ConstraintValidatorContext context) {
        // 关闭默认消息
        context.disableDefaultConstraintViolation();
        final Optional<User> optional = userDetailService.findById(updateUser.getId());
        if (optional.isEmpty()) {
            context.buildConstraintViolationWithTemplate("projectY.valid.UpdateUser.id.not-exist").addConstraintViolation();
            return false;
        }
        User user = optional.get();
        if (!user.getUsername().equals(updateUser.getUsername())) {
            final Optional<User> byUsername = userDetailService.findByUsername(updateUser.getUsername());
            if (byUsername.isPresent()) {
                context.buildConstraintViolationWithTemplate("projectY.valid.UpdateUser.username.exist").addConstraintViolation();
                return false;
            }
        }
        // 没有验证手机号码, 是否需要验证手机号码 ????????????
        if (!user.getMobilePhone().equals(updateUser.getMobilePhone())) {
            final Optional<User> byMobilePhone = userDetailService.findByMobilePhone(updateUser.getMobilePhone());
            if (byMobilePhone.isPresent()) {
                context.buildConstraintViolationWithTemplate("projectY.valid.UpdateUser.mobile-phone.exist").addConstraintViolation();
                return false;
            }
        }
        String createDateTime = updateUser.getCreateDateTime();
        if (createDateTime != null && !"".equals(createDateTime)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                new Timestamp(df.parse(createDateTime).getTime());
            } catch (ParseException e) {
                context.buildConstraintViolationWithTemplate("projectY.valid.UpdateUser.createDateTime.incorrect-format").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}

