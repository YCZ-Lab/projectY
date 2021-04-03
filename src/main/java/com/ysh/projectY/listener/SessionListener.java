package com.ysh.projectY.listener;

import com.ysh.projectY.entity.LoginLogs;
import com.ysh.projectY.entity.User;
import com.ysh.projectY.service.LoginLogsService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.security.web.session.HttpSessionIdChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Component
public class SessionListener {

    final LoginLogsService loginLogsService;

    public SessionListener(LoginLogsService loginLogsService) {
        this.loginLogsService = loginLogsService;
    }

    @EventListener
    private void onHttpSessionCreatedEvent(HttpSessionCreatedEvent event) {
//        System.out.println("HttpSessionCreatedEvent: " + ((HttpSessionCreatedEvent) event).getSession().getId());
    }

    @EventListener
    private void onHttpSessionIdChangedEvent(HttpSessionIdChangedEvent event) {
        String sessionID = event.getOldSessionId();
        String remarks = event.getNewSessionId();
//        System.out.println("HttpSessionIdChangedEvent: " + sessionID + " --> " + remarks);
        updateLoginLogs(sessionID, remarks);
    }

    @EventListener
    private void onHttpSessionDestroyedEvent(HttpSessionDestroyedEvent event) {
        String sessionID = event.getSession().getId();
//        System.out.println("HttpSessionDestroyedEvent: " + ((HttpSessionDestroyedEvent) event).getSession().getId());
        String remarks = "";
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            String requestURI = requestAttributes.getRequest().getRequestURI();
            if (requestURI.contains("logout")) {
                remarks = "User Logout";
            } else {
                remarks = requestURI;
            }
        } else {
            remarks = "Session Timeout";
        }
        updateLoginLogs(sessionID, remarks);
    }

//    @EventListener
//    private void onAbstractAuthenticationEvent(AbstractAuthenticationEvent event) {
//        System.out.println("1==> " + ((AbstractAuthenticationEvent) event).getAuthentication());
//        System.out.println("2==> " + ((AbstractAuthenticationEvent) event).getAuthentication().getClass());
//        System.out.println("3==> " + ((AbstractAuthenticationEvent) event).getAuthentication().getDetails());
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        System.out.println(requestAttributes.getRequest().getRequestURI());
//        if (event instanceof InteractiveAuthenticationSuccessEvent || event instanceof AuthenticationSuccessEvent) {
//            // rememberMe登陆时也会触发事件
////            requestAttributes.getRequest().getSession(false).setAttribute("userName", ((AbstractAuthenticationToken) event.getSource()).getName());
////            ((InteractiveAuthenticationSuccessEvent)event)
//        } else if (event instanceof AbstractAuthenticationFailureEvent || event instanceof LogoutSuccessEvent) {
////            requestAttributes.getRequest().getSession(false).removeAttribute("userName");
//        }
//    }


    @EventListener
    private void onInteractiveAuthenticationSuccessEvent(InteractiveAuthenticationSuccessEvent event) {
//        System.out.println("1==> " + ((InteractiveAuthenticationSuccessEvent) event).getAuthentication());
//        System.out.println("2==> " + ((InteractiveAuthenticationSuccessEvent) event).getAuthentication().getClass());
//        System.out.println("3==> " + ((InteractiveAuthenticationSuccessEvent) event).getAuthentication().getDetails());
//        System.out.println("4==> " + event.getGeneratedBy());
//        System.out.println("5==> " + event.getSource());
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
            loginLogsService.saveAndFlush(loginLogs);
        }
    }

//    @EventListener
//    private void onAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
//        System.out.println("6==> " + ((AuthenticationSuccessEvent) event).getAuthentication());
//        System.out.println("7==> " + ((AuthenticationSuccessEvent) event).getAuthentication().getClass());
//        System.out.println("8==> " + ((AuthenticationSuccessEvent) event).getAuthentication().getDetails());
//        System.out.println("9==> " + event.getSource());
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        System.out.println(requestAttributes.getRequest().getRequestURI());
//    }

    //监听不到对应的登陆失败事件
//    @EventListener
//    private void onAuthenticationFailureBadCredentialsEvent(AuthenticationFailureBadCredentialsEvent event) {
//        System.out.println("7==> " + ((AuthenticationFailureBadCredentialsEvent) event).getAuthentication());
//        System.out.println("8==> " + ((AuthenticationFailureBadCredentialsEvent) event).getAuthentication().getClass());
//        System.out.println("9==> " + ((AuthenticationFailureBadCredentialsEvent) event).getAuthentication().getDetails());
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        System.out.println(requestAttributes.getRequest().getRequestURI());
//    }

    private void updateLoginLogs(String sessionID, String remarks) {
        List<LoginLogs> loginLogs = loginLogsService.findBySessionID(sessionID);
        if (loginLogs != null && loginLogs.size() > 0) {
            for (LoginLogs loginLog : loginLogs) {
                loginLog.setLogoutDateTime(new Timestamp(System.currentTimeMillis()));
                loginLog.setRemarks(remarks + ", " + loginLog.getRemarks());
            }
            loginLogsService.saveAll(loginLogs);
        } else {
            LoginLogs incompleteLoginLogs = new LoginLogs();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
            }
            User user = null;
            if (auth != null) {
                try {
                    user = (User) auth.getPrincipal();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (user != null) {
                    incompleteLoginLogs.setUsername(user.getUsername());
                    incompleteLoginLogs.setMobilePhone(user.getMobilePhone());
                    incompleteLoginLogs.setPassword("");
                    incompleteLoginLogs.setLogoutDateTime(new Timestamp(System.currentTimeMillis()));
                    incompleteLoginLogs.setRemarks(remarks);
                    loginLogsService.saveAndFlush(incompleteLoginLogs);
                }
            }

        }
    }
}
