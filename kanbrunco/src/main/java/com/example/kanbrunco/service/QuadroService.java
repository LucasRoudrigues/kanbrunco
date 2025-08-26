package com.example.kanbrunco.service;

import com.example.kanbrunco.factory.QuadroCompartilhadoFactory;
import com.example.kanbrunco.factory.QuadroFactory;
import com.example.kanbrunco.factory.QuadroNormalFactory;
import com.example.kanbrunco.model.quadro.*;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.repository.QuadroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuadroService {

    @Autowired
    private QuadroRepository quadroRepository;

    public Quadro salvarQuadro(Quadro quadro) {
        return quadroRepository.save(quadro);
    }
    
    
    // Usa o factory method para criar o quadro normal
    public Quadro salvarQuadroNormal(String titulo, Usuario proprietario) {
        QuadroFactory factory = new QuadroNormalFactory();
        QuadroNormal quadro = (QuadroNormal) factory.criarQuadro();
        quadro.setTitulo(titulo);
        quadro.setProprietario(proprietario);
        return quadroRepository.save(quadro);
    }


    // Usa o factory method para criar o qaudro compartilhado
    public Quadro salvarQuadroCompartilhado(String titulo, Usuario proprietario) {
        QuadroFactory factory = new QuadroCompartilhadoFactory();
        QuadroCompartilhado quadro = (QuadroCompartilhado) factory.criarQuadro();
        quadro.setTitulo(titulo);
        quadro.setProprietario(proprietario);
        return quadroRepository.save(quadro);
    }

    // Metodo para listar quadros do usuario ou compartilhados
    public List<Quadro> listarDoUsuario(Usuario usuario) {
        List<Quadro> quadros = new ArrayList<>();
        quadros.addAll(quadroRepository.findByProprietario(usuario));
        quadros.addAll(quadroRepository.findQuadrosCompartilhadosComUsuario(usuario));
        return quadros;
    }
    
    public List<Quadro> listarTodos() {
        return quadroRepository.findAll();
    }

    // Metodos de busca e remoção de quadros
    public Optional<Quadro> buscarPorId(Long id) {
        return quadroRepository.findById(id);
    }

    public List<Quadro> buscarPorProprietario(Usuario proprietario) {
        return quadroRepository.findByProprietario(proprietario);
    }

    public void deletarQuadro(Long id) {
        quadroRepository.deleteById(id);
    }
    
    // Metodo para compartilhar quadros (exclusivo para usuario admin)
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
