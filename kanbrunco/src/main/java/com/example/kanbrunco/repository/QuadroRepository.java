package com.example.kanbrunco.repository;

import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.model.quadro.QuadroCompartilhado;
import com.example.kanbrunco.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuadroRepository extends JpaRepository<Quadro, Long> {
    List<Quadro> findByProprietario(Usuario proprietario);

    // Buscar quadros compartilhados
    @Query("SELECT q FROM QuadroCompartilhado q JOIN q.usuariosCompartilhados uc WHERE uc = :usuario")
    List<QuadroCompartilhado> findQuadrosCompartilhadosComUsuario(@Param("usuario") Usuario usuario);

}