package com.ufsm.csi.artconnect.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginErro implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException exception) 
      throws IOException, ServletException {
        request.getSession().setAttribute("error", "email ou senha incorretos");
        response.sendRedirect("login");
    }
}
