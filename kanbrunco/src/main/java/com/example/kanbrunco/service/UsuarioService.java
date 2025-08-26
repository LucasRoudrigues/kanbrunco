package com.example.kanbrunco.service;

import com.example.kanbrunco.factory.UsuarioFactory;
import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.model.quadro.QuadroCompartilhado;
import com.example.kanbrunco.model.quadro.QuadroNormal;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.model.usuario.UsuarioAdmin;
import com.example.kanbrunco.model.usuario.UsuarioBasico;
import com.example.kanbrunco.repository.QuadroRepository;
import com.example.kanbrunco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private QuadroRepository quadroRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Metodo para salvar usuario pelo factory method
    public Usuario salvarUsuario(String nome, String email, UsuarioFactory factory) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        Usuario usuario = factory.criarUsuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Metodo de remoção de usuario
    @Transactional
    public void deletarUsuario(Long id) {
        Optional<Usuario> usuarioParaDeletar = usuarioRepository.findById(id);
        if (usuarioParaDeletar.isPresent()) {
            Usuario usuario = usuarioParaDeletar.get();
            List<Quadro> quadrosDoUsuario = quadroRepository.findByProprietario(usuario);
            for (Quadro quadro : quadrosDoUsuario) {
                if (quadro instanceof QuadroCompartilhado) {
                    QuadroCompartilhado quadroCompartilhado = (QuadroCompartilhado) quadro;
                    if (!quadroCompartilhado.getUsuariosCompartilhados().isEmpty()) {
                        Usuario novoProprietario = quadroCompartilhado.getUsuariosCompartilhados().get(0);
                        quadroCompartilhado.setProprietario(novoProprietario);
                        quadroCompartilhado.removerCompartilhamento(usuario);
                        quadroRepository.save(quadroCompartilhado);
                    } else {
                        quadroRepository.delete(quadroCompartilhado);
                    }
                } else if (quadro instanceof QuadroNormal) {
                    quadroRepository.delete(quadro);
                }
            }

            List<QuadroCompartilhado> quadrosComOutros = quadroRepository.findQuadrosCompartilhadosComUsuario(usuario);
            for (QuadroCompartilhado quadro : quadrosComOutros) {
                quadro.removerCompartilhamento(usuario);
                quadroRepository.save(quadro);
            }

            usuarioRepository.deleteById(id);
        }
    }
}