package com.example.kanbrunco.service;

import com.example.kanbrunco.model.comentario.Comentario;
import com.example.kanbrunco.model.cartao.Cartao;
import com.example.kanbrunco.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public Comentario salvarComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    public List<Comentario> listarTodos() {
        return comentarioRepository.findAll();
    }

    public Optional<Comentario> buscarPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    public List<Comentario> buscarPorCartao(Cartao cartao) {
        return comentarioRepository.findByCartao(cartao);
    }

    public void deletarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}