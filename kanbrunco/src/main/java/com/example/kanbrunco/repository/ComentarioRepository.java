package com.example.kanbrunco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kanbrunco.model.comentario.Comentario;

@Repository
public class ComentarioRepository extends JpaRepository<Comentario, Long> {

}
