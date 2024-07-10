package com.ufsm.csi.artconnect.service.impl;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.PerfilForm;
import com.ufsm.csi.artconnect.form.UsuarioForm;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.ArtistRepository;
import com.ufsm.csi.artconnect.repository.UsuarioRepository;
import com.ufsm.csi.artconnect.service.UsuarioService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final ArtistRepository artistRepository;

    private EntityManager entityManager;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ArtistRepository artistRepository, PasswordEncoder passwordEncoder, EntityManager entityManager) {
        this.usuarioRepository = usuarioRepository;
        this.artistRepository = artistRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    @Override
    public List<UsuarioDto> findAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::mapToUsuarioDto).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDto> findAllArtistas(String filtro) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
        Root<Usuario> root = query.from(Usuario.class);
        query.select(root);
        query.where(cb.equal(root.get("tipousuario"), 1));
        
        if(filtro != null){
            query.where(cb.like(root.get("nomeusuario"), "%" + filtro.toLowerCase() + "%"));
        }
        
        TypedQuery<Usuario> result = this.entityManager.createQuery(query);
        List<Usuario> artistas = result.getResultList();
        return artistas.stream().map(this::mapToUsuarioDto).collect(Collectors.toList());
    }

    @Override
    public UsuarioDto findUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUsuarioDto(usuario);
    }

    private UsuarioDto mapToUsuarioDto(Usuario usuario) {
        return UsuarioDto.builder()
                .idusuario(usuario.getIdusuario())
                .nomeusuario(usuario.getNomeusuario())
                .emailusuario(usuario.getEmailusuario())
                .profilepicture(usuario.getProfilepicture())
                .description(usuario.getDescription())
                .tipousuario(usuario.getTipousuario())
                .build();
    }

    @Override
    public UsuarioDto salvar(UsuarioForm usuarioForm) {
        Usuario u = new Usuario();
        u.setEmailusuario(usuarioForm.getEmail());
        u.setNomeusuario(usuarioForm.getNome());
        u.setTipousuario(usuarioForm.getArtista() ? 1 : 0);
        u.setSenhausuario(passwordEncoder.encode(usuarioForm.getSenha()));
        this.usuarioRepository.save(u);
        return mapToUsuarioDto(u);
    }

    @Override
    public UsuarioDto update(PerfilForm usuarioForm, HttpServletRequest request) {
        Usuario currentUser = (Usuario) request.getSession().getAttribute("usuario");
        Usuario usuario = this.usuarioRepository.findByEmailusuario(currentUser.getEmailusuario()).get();
        usuario.setDescription(usuarioForm.getDescription());
        usuario.setNomeusuario(usuarioForm.getNome());
        usuario = this.usuarioRepository.save(usuario);
        request.getSession().setAttribute("usuario", usuario);
        return mapToUsuarioDto(usuario);
    }

    @Override
    public UsuarioDto findUsuarioByEmail(String email) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailusuario(email);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            return UsuarioDto.builder()
                    .idusuario(usuario.getIdusuario())
                    .nomeusuario(usuario.getNomeusuario())
                    .emailusuario(usuario.getEmailusuario())
                    .profilepicture(usuario.getProfilepicture())
                    .description(usuario.getDescription())
                    .tipousuario(usuario.getTipousuario())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public UsuarioDto deactivateAccount(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setAtivo(false); // Desativa a conta
        usuarioRepository.save(usuario);
        return null;
    }

}
