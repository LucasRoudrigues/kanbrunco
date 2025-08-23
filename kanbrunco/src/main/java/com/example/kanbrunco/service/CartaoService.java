package com.example.kanbrunco.service;

import com.example.kanbrunco.model.cartao.Cartao;
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

    public Cartao salvarCartao(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    public List<Cartao> listarTodos() {
        return cartaoRepository.findAll();
    }

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