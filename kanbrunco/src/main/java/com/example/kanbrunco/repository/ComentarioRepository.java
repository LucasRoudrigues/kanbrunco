package com.example.kanbrunco.repository;

import com.example.kanbrunco.model.comentario.Comentario;
import com.example.kanbrunco.model.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByCartao(Cartao cartao);
}