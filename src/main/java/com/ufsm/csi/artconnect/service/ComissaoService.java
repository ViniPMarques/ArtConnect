package com.ufsm.csi.artconnect.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.form.ComissaoForm;
import com.ufsm.csi.artconnect.model.Comissao;
import com.ufsm.csi.artconnect.model.Obra;
import com.ufsm.csi.artconnect.repository.ComissaoRepository;
import com.ufsm.csi.artconnect.repository.ObraRepository;
import com.ufsm.csi.artconnect.repository.UsuarioRepository;

@Service
public class ComissaoService {
    @Autowired
    private ComissaoRepository comissaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Comissao> getAllByArtista(Long idartista){
        return comissaoRepository.findByIdartista(idartista);
    }

    public Comissao findByArtista(Long idartista, Long idcomissao) throws Exception{
        return comissaoRepository.findByIdartistaAndIdComissao(idartista, idcomissao).orElseThrow(() -> new Exception("não encontrado"));
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(ComissaoForm c, UsuarioDto u) throws Exception {
        Comissao comissao = null;
        if(c.getId() != null){
            comissao = this.findByArtista(u.getIdusuario(), c.getId());
        }
        else{
            comissao = new Comissao();
            comissao.setArtista(usuarioRepository.findById(u.getIdusuario()).get());
        }
        comissao.setDescricao(c.getDescricao());
        comissao.setValor(c.getValor());
        comissaoRepository.save(comissao);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(ComissaoForm c, UsuarioDto u, Long id) throws Exception {
        Comissao comissao = comissaoRepository.findByIdartistaAndIdComissao(u.getIdusuario(), id).orElseThrow(() -> new Exception("não encontrado"));
        comissao.setDescricao(c.getDescricao());
        comissao.setValor(c.getValor());
        comissaoRepository.save(comissao);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, UsuarioDto u) throws Exception {
        Optional<Comissao> comissao = comissaoRepository.findByIdartistaAndIdComissao(u.getIdusuario(), id);

        if(!comissao.isPresent()){
            throw new RuntimeException("comissao não pertence ao artista");
        }

        comissaoRepository.delete(comissao.get());
    }
}
