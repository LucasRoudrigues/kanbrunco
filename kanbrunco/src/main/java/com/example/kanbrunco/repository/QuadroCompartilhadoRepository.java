package com.example.kanbrunco.repository;

import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.model.quadro.QuadroCompartilhado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuadroCompartilhadoRepository extends JpaRepository<QuadroCompartilhado, Long> {
}