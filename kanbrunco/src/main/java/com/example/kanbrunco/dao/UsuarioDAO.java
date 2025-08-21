package com.example.kanbrunco.dao;

import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.model.usuario.UsuarioAdmin;
import com.example.kanbrunco.model.usuario.UsuarioBasico;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
public class UsuarioDAO {

    private static final String ARQUIVO_USUARIOS = "usuarios.txt";
    private Long proximoId;

    public UsuarioDAO() {
        this.proximoId = determinarProximoId();
    }

    private Long determinarProximoId() {
        return carregarTodos().stream()
                .mapToLong(usuario -> usuario.getId())
                .max()
                .orElse(0L) + 1;
    }
    
    // Método para salvar um novo usuário
    public Usuario salvar(Usuario usuario) {
    	
    	Optional<Usuario> usuarioExistente = buscarPorEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            // Se o usuário existir, lança uma exceção para evitar duplicidade
            throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
        }
    	
        if (usuario.getId() == null) {
            usuario.setId(proximoId++);
        }
        
        List<Usuario> usuarios = carregarTodos();
        usuarios = usuarios.stream()
                .filter(u -> !u.getId().equals(usuario.getId()))
                .collect(Collectors.toList());
        usuarios.add(usuario);

        salvarTodos(usuarios);
        return usuario;
    }

    // Método para buscar todos os usuários
    public List<Usuario> carregarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4) {
                    String tipo = partes[0];
                    Long id = Long.parseLong(partes[1]);
                    String nome = partes[2];
                    String email = partes[3];

                    Usuario usuario;
                    if ("basico".equalsIgnoreCase(tipo)) { // Verifica o tipo correto
                        usuario = new UsuarioBasico();
                    } else if ("admin".equalsIgnoreCase(tipo)) {
                        usuario = new UsuarioAdmin();
                    } else {
                        continue;
                    }

                    usuario.setId(id);
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            // O arquivo pode não existir na primeira execução
        }
        return usuarios;
    }
    
    // Métodos de busca e deleção continuam iguais
    public Optional<Usuario> buscarPorId(Long id) {
        return carregarTodos().stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return carregarTodos().stream()
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Usuario atualizar(Usuario usuarioAtualizado) {
        List<Usuario> usuarios = carregarTodos();
        usuarios = usuarios.stream()
                .map(u -> u.getId().equals(usuarioAtualizado.getId()) ? usuarioAtualizado : u)
                .collect(Collectors.toList());
        salvarTodos(usuarios);
        return usuarioAtualizado;
    }

    public void deletar(Long id) {
        List<Usuario> usuarios = carregarTodos();
        usuarios.removeIf(usuario -> usuario.getId().equals(id));
        salvarTodos(usuarios);
    }
    
    // Método auxiliar para reescrever todos os usuários no arquivo
    private void salvarTodos(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario usuario : usuarios) {
                String tipo = usuario instanceof UsuarioAdmin ? "admin" : "basico"; // Verifica o tipo correto
                String linha = tipo + ";" + usuario.getId() + ";" + usuario.getNome() + ";" + usuario.getEmail() + "\n";
                writer.write(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}