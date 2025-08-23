package com.example.kanbrunco.service;

import com.example.kanbrunco.model.quadro.*;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.repository.QuadroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuadroService {

    @Autowired
    private QuadroRepository quadroRepository;

    public Quadro salvarQuadro(Quadro quadro) {
        return quadroRepository.save(quadro);
    }
    
    
    public Quadro salvarQuadroNormal(String titulo, Usuario proprietario) {
        QuadroNormal quadro = new QuadroNormal();
        quadro.setTitulo(titulo);
        quadro.setProprietario(proprietario);
        return quadroRepository.save(quadro);
    }


    public Quadro salvarQuadroCompartilhado(String titulo, Usuario proprietario) {
        QuadroCompartilhado quadro = new QuadroCompartilhado();
        quadro.setTitulo(titulo);
        quadro.setProprietario(proprietario);
        return quadroRepository.save(quadro);
    }
    
    public List<Quadro> listarTodos() {
        return quadroRepository.findAll();
    }

    public Optional<Quadro> buscarPorId(Long id) {
        return quadroRepository.findById(id);
    }

    public List<Quadro> buscarPorProprietario(Usuario proprietario) {
        return quadroRepository.findByProprietario(proprietario);
    }

    public void deletarQuadro(Long id) {
        quadroRepository.deleteById(id);
    }
    
    @Transactional
    public QuadroCompartilhado compartilharQuadro(Long quadroId, Usuario usuarioParaCompartilhar) {
        Optional<Quadro> quadroOpt = quadroRepository.findById(quadroId);
        if (quadroOpt.isPresent() && quadroOpt.get() instanceof QuadroCompartilhado) {
            QuadroCompartilhado quadro = (QuadroCompartilhado) quadroOpt.get();
            quadro.compartilharComUsuario(usuarioParaCompartilhar);
            return quadroRepository.save(quadro);
        }
        return null;
    }
}
