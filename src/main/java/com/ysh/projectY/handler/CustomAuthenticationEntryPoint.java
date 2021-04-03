package com.ysh.projectY.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysh.projectY.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // 当没有认证, 访问需要认证的资源时会到此EntryPoint
    // 默认情况是重定向到登陆页面
    @Value("${projectY.allowed-origins}")
    private String allowedOrigins;

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException {
        authException.printStackTrace();
        resp.setStatus(401);
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT");
        resp.setHeader("Access-Control-Allow-Origin", allowedOrigins);
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Max-Age", "1800");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        JsonResponse loginFailure = null;
        if (authException instanceof InsufficientAuthenticationException) {
            loginFailure = JsonResponse.success(HttpStatus.UNAUTHORIZED.value(), "projectY.authentication.failure.InsufficientAuthenticationException", authException, authException.toString());
        } else if (authException instanceof Exception) {
            loginFailure = JsonResponse.success(HttpStatus.UNAUTHORIZED.value(), "projectY.authentication.failure.Exception", authException, authException.toString());
        }
        out.write(new ObjectMapper().writeValueAsString(loginFailure));
        out.flush();
        out.close();
    }
}
