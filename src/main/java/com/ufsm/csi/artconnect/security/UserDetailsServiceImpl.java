package com.ufsm.csi.artconnect.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.UsuarioRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> user = usuarioRepository.findByEmailusuario(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("user not available");
        }
        return new org.springframework.security.core.userdetails.User(
            user.get().getEmailusuario(), 
            user.get().getSenhausuario(), 
            true, 
            true,
            true, 
            true, 
            new ArrayList<>()
        );
	}
}
