package com.ufsm.csi.artconnect.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Logout extends SimpleUrlLogoutSuccessHandler{

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        if(session != null){
            session.removeAttribute("usuario");
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath()+"/login");
        super.onLogoutSuccess(request, response, authentication);
    }
    
}
