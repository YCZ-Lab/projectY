package com.ysh.projectY.listener;

import com.ysh.projectY.entity.LoginLogs;
import com.ysh.projectY.entity.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.event.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.security.web.session.HttpSessionIdChangedEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

public class EventListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ServletRequestHandledEvent) {
            return;
        }
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (requestAttributes != null) {
//            System.out.println("1==> " + requestAttributes.getRequest().getRequestURI());
//        } else {
//            System.out.println("2==> requestAttributes is Null");
//        }
//        System.out.println("3==> " + event.getClass());
//        System.out.println("4==> " + event.getSource().getClass());
        if (event instanceof AbstractAuthenticationEvent) {
            System.out.println("8==> " + ((AbstractAuthenticationEvent) event).getAuthentication());
            System.out.println("9==> " + ((AbstractAuthenticationEvent) event).getAuthentication().getClass());
            System.out.println("0==> " + ((AbstractAuthenticationEvent) event).getAuthentication().getDetails());
//            if (event instanceof InteractiveAuthenticationSuccessEvent || event instanceof AuthenticationSuccessEvent) {
//                // rememberMe登陆时也会触发事件
//                requestAttributes.getRequest().getSession(false).setAttribute("userName", ((AbstractAuthenticationToken) event.getSource()).getName());
//            } else if (event instanceof AbstractAuthenticationFailureEvent || event instanceof LogoutSuccessEvent) {
//                requestAttributes.getRequest().getSession(false).removeAttribute("userName");
//            }
        }
        if (event.getSource().getClass().getName().contains("RememberMe")) {
            LoginLogs loginLogs = new LoginLogs();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                User user = (User) auth.getPrincipal();
                loginLogs.setUsername(user.getUsername());
                loginLogs.setMobilePhone(user.getMobilePhone());
                loginLogs.setPassword("");
            }
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                final HttpServletRequest req = requestAttributes.getRequest();
                if (req != null) {
                    String requestURI = req.getRequestURI();
                    loginLogs.setLoginURL(requestURI);
                    final HttpSession session = req.getSession(true);
                    if (session != null) {
                        loginLogs.setSessionID(session.getId());
                    }
                    loginLogs.setClientIP(req.getRemoteAddr());
                }
            } else {
                loginLogs.setLoginURL("unKnown");
                loginLogs.setClientIP(((InteractiveAuthenticationSuccessEvent) event).getAuthentication().getDetails().toString());
            }
            loginLogs.setStatus("SUCCESS");
            loginLogs.setLoginDateTime(new Timestamp(System.currentTimeMillis()));
            loginLogs.setRemarks("Remember Me Login");
//            loginLogsService.saveAndFlush(loginLogs);
            System.out.println("Logs: " + loginLogs);
        }

        System.out.println("=========================================================");
    }
}
