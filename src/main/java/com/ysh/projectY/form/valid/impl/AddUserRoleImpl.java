package com.ysh.projectY.form.valid.impl;

import com.ysh.projectY.entity.Role;
import com.ysh.projectY.entity.User;
import com.ysh.projectY.form.valid.AddUserRole;
import com.ysh.projectY.service.RoleService;
import com.ysh.projectY.service.UserDetailService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

public class AddUserRoleImpl implements ConstraintValidator<AddUserRole, com.ysh.projectY.form.AddUserRole> {

    UserDetailService userDetailService;
    RoleService roleService;

    public AddUserRoleImpl(UserDetailService userDetailService, RoleService roleService) {
        this.userDetailService = userDetailService;
        this.roleService = roleService;
    }

    @Override
    public boolean isValid(com.ysh.projectY.form.AddUserRole addUserRole, ConstraintValidatorContext context) {
        // 关闭默认消息
        context.disableDefaultConstraintViolation();
        final Optional<User> o_user = userDetailService.findById(addUserRole.getUserID());
        if (o_user.isEmpty()) {
            context.buildConstraintViolationWithTemplate("projectY.valid.AddUserRole.userID.not-exist").addConstraintViolation();
            return false;
        }
        final Optional<Role> o_role = roleService.findById(addUserRole.getRoleID());
        if (o_role.isEmpty()) {
            context.buildConstraintViolationWithTemplate("projectY.valid.AddUserRole.roleID.not-exist").addConstraintViolation();
            return false;
        }
        User user = o_user.get();
        Role role = o_role.get();
        final List<Role> roles = user.getRoles();
        if (roles.contains(role)) {
            context.buildConstraintViolationWithTemplate("projectY.valid.AddUserRole.roleID.user-already-has-this-role").addConstraintViolation();
            return false;
        }
        return true;
    }
}

