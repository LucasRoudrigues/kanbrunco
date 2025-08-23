package com.example.kanbrunco.repository;

import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.model.quadro.Quadro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListaTarefasRepository extends JpaRepository<ListaTarefas, Long> {
    List<ListaTarefas> findByQuadro(Quadro quadro);
}