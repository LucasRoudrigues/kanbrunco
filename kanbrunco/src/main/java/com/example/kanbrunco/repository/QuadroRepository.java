package com.example.kanbrunco.repository;

import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuadroRepository extends JpaRepository<Quadro, Long> {
    List<Quadro> findByProprietario(Usuario proprietario);
}