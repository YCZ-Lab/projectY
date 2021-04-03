package com.ysh.projectY.service;

import com.ysh.projectY.dao.UserDao;
import com.ysh.projectY.entity.Role;
import com.ysh.projectY.entity.User;
import com.ysh.projectY.form.*;
import com.ysh.projectY.utils.MethodResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleService roleService;

    @Value("${projectY.api-base-path}")
    private String apiBasePath;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("account not found!!!");
        }
//        erasePassword(user);  不能在此清除密码
//        getUserRoles(user);
        return user;
    }

    public Optional<User> findById(Integer id) {
        return userDao.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public Optional<User> findByMobilePhone(String mobilePhone) {
        return userDao.findByMobilePhone(mobilePhone);
    }

    public void saveAndFlush(User user) {
        userDao.saveAndFlush(user);
    }

    // 获取当前用户信息
    public MethodResponse getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        try {
            user = (User) authentication.getPrincipal();
        } catch (ClassCastException e) {
            final Optional<User> guest = userDao.findById(99999);
            user = guest.get();
//            return MethodResponse.failure("projectY.UserService.getUser.failure.ClassCastException", e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return MethodResponse.failure("projectY.UserService.getUser.failure.Exception", e.toString());
        }
        return MethodResponse.success("projectY.UserService.getUser.success", "", user);
    }

    public Page<User> getUsers(String nickname, String username, String mobilePhone, Pageable pageable) {
        final Page<User> page = userDao.findAllByNicknameContainingAndUsernameContainingAndMobilePhoneContaining(nickname, username, mobilePhone, pageable);
        return page;
    }

    public MethodResponse registerUser(RegisterUser registerUser) {
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setMobilePhone(registerUser.getMobilePhone());
        user.setNickname(registerUser.getNickname());
        user.setAvatarURL(registerUser.getAvatarURL());
        // 加密密码
        user.setPassword(new BCryptPasswordEncoder(12).encode(registerUser.getPassword()));
        // 设置创建时间
        user.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        // 设置用户是否启用
        user.setEnabled(true);
        // 设置用户是否锁定
        user.setLocked(false);
        // 设置用户角色
        List<Role> roles = new ArrayList<>();
        final Optional<Role> optional = roleService.findById(2);
        if (optional.isPresent()) {
            roles.add(optional.get());
        }
        user.setRoles(roles);
        try {
            userDao.saveAndFlush(user);
        } catch (Exception e) {
            e.printStackTrace();
            return MethodResponse.failure("projectY.UserService.registerUser.failure.Exception", e.toString());
        }
        return MethodResponse.success("projectY.UserService.registerUser.success");
    }

    public MethodResponse updateUser(UpdateUser updateUser) {
        final Optional<User> optional = userDao.findById(updateUser.getId());
        User user = optional.get();
        String newNickName = updateUser.getNickname();
        if (newNickName != null && !"".equals(newNickName)) {
            user.setNickname(newNickName);
        }
        user.setUsername(updateUser.getUsername());
        user.setMobilePhone(updateUser.getMobilePhone());
        String newPassword = updateUser.getPassword();
        if (newPassword != null && !"".equals(newPassword)) {
            user.setPassword(new BCryptPasswordEncoder(12).encode(newPassword));
        }
        String newAvatarURL = updateUser.getAvatarURL();
        if (newAvatarURL != null && !"".equals(newAvatarURL)) {
            user.setAvatarURL(newAvatarURL);
        }
        String newCreateDateTime = updateUser.getCreateDateTime();
        if (newCreateDateTime != null && !"".equals(newCreateDateTime)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                user.setCreateDateTime(new Timestamp(df.parse(updateUser.getCreateDateTime()).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Boolean newEnabled = updateUser.getEnabled();
        if (newEnabled != null) {
            user.setEnabled(newEnabled);
        }
        Boolean newLocked = updateUser.getLocked();
        if (newLocked != null) {
            user.setLocked(newLocked);
        }
        try {
            userDao.saveAndFlush(user);
        } catch (Exception e) {
            e.printStackTrace();
            return MethodResponse.failure("projectY.UserService.updateUser.failure.Exception", e.toString());
        }
        return MethodResponse.success("projectY.UserService.updateUser.success");
    }

    public MethodResponse deleteUser(int id) {
        final Optional<User> optional = userDao.findById(id);
        if (optional.isEmpty()) {
            return MethodResponse.failure("projectY.UserService.deleteUser.failure.id-not-exist");
        }
        userDao.delete(optional.get());
        return MethodResponse.success("projectY.UserService.deleteUser.success");
    }

    public MethodResponse deleteUsers(List<Integer> ids) {
        StringBuilder detail = new StringBuilder();
        for (Integer id : ids) {
            final Optional<User> optional = userDao.findById(id);
            if (optional.isEmpty()) {
                detail.append(id).append(", ");
                continue;
            }
            userDao.delete(optional.get());
        }
        if (!"".equals(detail.toString())) {
            detail = new StringBuilder("[ " + detail.substring(0, detail.length() - 2) + " ] User not Exist");
            return MethodResponse.failure("projectY.UserService.deleteUsers.failure.ids-not-exist", detail.toString(), null);
        }
        return MethodResponse.success("projectY.UserService.deleteUsers.success");
    }

    public MethodResponse createUser(CreateUser createUser) {
        User user = new User();
        user.setUsername(createUser.getUsername());
        user.setMobilePhone(createUser.getMobilePhone());
        user.setNickname(createUser.getNickname());
        user.setAvatarURL(createUser.getAvatarURL());
        // 加密密码
        user.setPassword(new BCryptPasswordEncoder(12).encode(createUser.getPassword()));
        // 设置创建时间
        user.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        // 设置用户是否启用
        user.setEnabled(true);
        // 设置用户是否锁定
        user.setLocked(false);
        // 设置用户角色
        List<Role> roles = new ArrayList<>();
        final Optional<Role> optional = roleService.findById(2);
        if (optional.isPresent()) {
            roles.add(optional.get());
        }
        user.setRoles(roles);
        try {
            userDao.saveAndFlush(user);
        } catch (Exception e) {
            e.printStackTrace();
            return MethodResponse.failure("projectY.UserService.createUser.failure.Exception", e.toString());
        }
        return MethodResponse.success("projectY.UserService.createUser.success");
    }

    public MethodResponse createAvatar(MultipartFile uploadFile, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
//        String realPath = req.getSession(false).getServletContext().getRealPath("/uploadFile/");
        final String property = System.getProperty("catalina.home");
        String realPath = property + File.separator + "work" + File.separator + "resource" + File.separator + "avatars" + File.separator;
//        System.out.println("realPath: " + realPath);
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
//        System.out.println(folder.getAbsolutePath());
//        System.out.println(folder.getPath());
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            uploadFile.transferTo(new File(folder, newName));
            String filePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + apiBasePath + "/user/avatars/" + format + newName;
            return MethodResponse.success("projectY.UserService.createAvatar.success", "", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return MethodResponse.failure("projectY.UserService.createAvatar.failure.IOException", e.toString(), e);
        } catch (Exception e) {
            e.printStackTrace();
            return MethodResponse.failure("projectY.UserService.createAvatar.failure.Exception", e.toString(), e);
        }
    }

    public MethodResponse addUserRole(AddUserRole addUserRole) {
        final Optional<User> o_user = userDao.findById(addUserRole.getUserID());
        User user = o_user.get();
        final Optional<Role> o_role = roleService.findById(addUserRole.getRoleID());
        Role role = o_role.get();
        user.getRoles().add(role);
        try {
            userDao.saveAndFlush(user);
        } catch (Exception e) {
            e.printStackTrace();
            return MethodResponse.failure("projectY.UserService.addUserRole.failure.Exception", e.toString());
        }
        return MethodResponse.success("projectY.UserService.addUserRole.success");
    }

    public MethodResponse deleteUserRole(DeleteUserRole deleteUserRole) {
//        System.out.println(deleteUserRole.getUserID() + " - " + deleteUserRole.getRoleID());
        final Optional<User> o_user = userDao.findById(deleteUserRole.getUserID());
        User user = o_user.get();
        final Optional<Role> o_role = roleService.findById(deleteUserRole.getRoleID());
        Role role = o_role.get();
        user.getRoles().remove(role);
        try {
            userDao.saveAndFlush(user);
        } catch (Exception e) {
            e.printStackTrace();
            return MethodResponse.failure("projectY.UserService.deleteUserRole.failure.Exception", e.toString());
        }
        return MethodResponse.success("projectY.UserService.deleteUserRole.success");
    }
}
