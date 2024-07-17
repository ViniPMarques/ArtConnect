package com.ufsm.csi.artconnect.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufsm.csi.artconnect.form.PedidoForm;
import com.ufsm.csi.artconnect.model.Comissao;
import com.ufsm.csi.artconnect.model.Pedido;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.ComissaoRepository;
import com.ufsm.csi.artconnect.repository.PedidoRepository;

@Service
public class PedidoService {
    @Autowired
    private ComissaoRepository comissaoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findByArtista(Long idartista){
        return pedidoRepository.findByIdartista(idartista);
    }

    @Transactional(rollbackFor = Exception.class)
    public Pedido save(PedidoForm pedidoForm, Usuario usuario) throws Exception{
        Comissao comissao = comissaoRepository.findById(pedidoForm.getIdcomissao()).orElseThrow(() -> new Exception("comissão não encontrada"));
        Pedido pedido = new Pedido();
        pedido.setCliente(usuario);
        pedido.setArtista(comissao.getArtista());
        pedido.setComissao(comissao);
        pedido.setDatacomissao(LocalDateTime.now());
        pedido.setDatafinalizacao(null);
        pedido.setValorcomissao(0d);
        pedido.setDescricao(pedidoForm.getDescricao());
        pedidoRepository.save(pedido);
        return pedido;
    }
}
