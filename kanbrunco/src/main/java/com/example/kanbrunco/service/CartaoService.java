package com.example.kanbrunco.service;

import com.example.kanbrunco.model.cartao.*;
import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    //Métodos para salvar os cartões
    public Cartao salvarCartao(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    public Cartao salvarCartaoSimples(String titulo, String descricao) {
        CartaoSimples cartao = new CartaoSimples();
        cartao.setTitulo(titulo);
        cartao.setDescricao(descricao);
        return cartaoRepository.save(cartao);
    }

    public Cartao salvarCartaoComChecklist(String titulo, String descricao) {
        CartaoComChecklist cartao = new CartaoComChecklist();
        cartao.setTitulo(titulo);
        cartao.setDescricao(descricao);
        return cartaoRepository.save(cartao);
    }
    
    //Listar Cartões
    public List<Cartao> listarTodos() {
        return cartaoRepository.findAll();
    }

    //Métodos de Busca e Remoção
    public Optional<Cartao> buscarPorId(Long id) {
        return cartaoRepository.findById(id);
    }

    public List<Cartao> buscarPorLista(ListaTarefas lista) {
        return cartaoRepository.findByLista(lista);
    }

    public void deletarCartao(Long id) {
        cartaoRepository.deleteById(id);
    }
}
