package com.example.kanbrunco.service;

import com.example.kanbrunco.model.comentario.Comentario;
import com.example.kanbrunco.model.cartao.Cartao;
import com.example.kanbrunco.repository.ComentarioRepository;
import com.example.kanbrunco.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;
    
    //Salvar Comentarios
    public Comentario salvarComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    public List<Comentario> listarTodos() {
        return comentarioRepository.findAll();
    }

    //Métodos de Busca
    public Optional<Comentario> buscarPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    public List<Comentario> buscarPorCartao(Cartao cartao) {
        return comentarioRepository.findByCartao(cartao);
    }

    public void deletarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
    
    //Métodos de adição e remoção de comentarios fazendo a conexão com seu devido cartão
    public Comentario adicionarComentario(Long cartaoId, Comentario comentario) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(cartaoId);
        if (cartaoOptional.isPresent()) {
            Cartao cartao = cartaoOptional.get();
            comentario.setCartao(cartao);
            return comentarioRepository.save(comentario);
        }
        return null;		// Retorna nulo caso não ache o cartão
    }

    public void removerComentario(Long comentarioId) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(comentarioId);
        if (comentarioOptional.isPresent()) {
            Comentario comentario = comentarioOptional.get();
            comentarioRepository.delete(comentario);
        }
    }
}
