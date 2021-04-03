package com.ysh.projectY.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysh.projectY.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Value("${projectY.allowed-origins}")
    private String allowedOrigins;

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        accessDeniedException.printStackTrace();
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT");
        resp.setHeader("Access-Control-Allow-Origin", allowedOrigins);
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Max-Age", "1800");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        JsonResponse loginFailure = null;
        if (accessDeniedException instanceof AccessDeniedException) {
            loginFailure = JsonResponse.success(HttpStatus.FORBIDDEN.value(), "projectY.access.failure..AccessDeniedException", accessDeniedException, accessDeniedException.toString());
        } else if (accessDeniedException instanceof Exception) {
            loginFailure = JsonResponse.success(HttpStatus.FORBIDDEN.value(), "projectY.access.failure.Exception", accessDeniedException, accessDeniedException.toString());
        }
        out.write(new ObjectMapper().writeValueAsString(loginFailure));
        out.flush();
        out.close();

    }
}
