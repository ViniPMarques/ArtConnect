package com.ufsm.csi.artconnect.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.UsuarioRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginSuccess implements AuthenticationSuccessHandler{
    @Autowired
	private UsuarioRepository usuarioRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Optional<Usuario> usuario = usuarioRepository.findByEmailusuario(authentication.getName());
        HttpSession session = request.getSession();
        if(session != null)
            session.setAttribute("usuario", usuario.get());

        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            response.sendRedirect(request.getContextPath()+"/artistPage");
        }
        else{
            response.sendRedirect(request.getContextPath()+"/usuario");
        }
    }
}
