package com.ysh.projectY.form.valid.impl;

import com.ysh.projectY.entity.Role;
import com.ysh.projectY.entity.User;
import com.ysh.projectY.form.valid.DeleteUserRole;
import com.ysh.projectY.service.RoleService;
import com.ysh.projectY.service.UserDetailService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Optional;

public class DeleteUserRoleImpl implements ConstraintValidator<DeleteUserRole, com.ysh.projectY.form.DeleteUserRole> {

    UserDetailService userDetailService;
    RoleService roleService;

    public DeleteUserRoleImpl(UserDetailService userDetailService, RoleService roleService) {
        this.userDetailService = userDetailService;
        this.roleService = roleService;
    }

    @Override
    public boolean isValid(com.ysh.projectY.form.DeleteUserRole deleteUserRole, ConstraintValidatorContext context) {
        // 关闭默认消息
        context.disableDefaultConstraintViolation();
        final Optional<User> o_user = userDetailService.findById(deleteUserRole.getUserID());
        if (o_user.isEmpty()) {
            context.buildConstraintViolationWithTemplate("projectY.valid.DeleteUserRole.userID.not-exist").addConstraintViolation();
            return false;
        }
        final Optional<Role> o_role = roleService.findById(deleteUserRole.getRoleID());
        if (o_role.isEmpty()) {
            context.buildConstraintViolationWithTemplate("projectY.valid.DeleteUserRole.roleID.not-exist").addConstraintViolation();
            return false;
        }
        User user = o_user.get();
        Role role = o_role.get();
        final List<Role> roles = user.getRoles();
        if (!roles.contains(role)) {
            context.buildConstraintViolationWithTemplate("projectY.valid.DeleteUserRole.roleID.user-don't-have-this-role").addConstraintViolation();
            return false;
        }
        return true;
    }
}

