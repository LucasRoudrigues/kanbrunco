package com.example.kanbrunco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kanbrunco.model.lista.ListaTarefas;

@Repository
public interface ListaRepository extends JpaRepository<ListaTarefas, Long> {

}
