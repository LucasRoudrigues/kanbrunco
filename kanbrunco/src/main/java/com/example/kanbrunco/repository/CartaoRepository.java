package com.example.kanbrunco.repository;

import com.example.kanbrunco.model.cartao.Cartao;
import com.example.kanbrunco.model.lista.ListaTarefas;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    List<Cartao> findByLista(ListaTarefas lista);
}