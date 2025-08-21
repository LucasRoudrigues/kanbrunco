package com.example.kanbrunco.dao;

import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.model.quadro.QuadroCompartilhado;
import com.example.kanbrunco.model.usuario.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QuadroDAO {

    private static final String ARQUIVO_QUADROS = "quadros.txt";
    private Long proximoId;
    private final UsuarioDAO usuarioDAO;

    public QuadroDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.proximoId = determinarProximoId();
    }

    private Long determinarProximoId() {
        return carregarTodos().stream()
                .mapToLong(quadro -> quadro.getId() != null ? quadro.getId() : 0L)
                .max()
                .orElse(0L) + 1;
    }

    // Método para salvar um novo quadro
    public Quadro salvar(Quadro quadro) {
        if (quadro.getId() == null) {
            quadro.setId(proximoId++);
        }
        
        List<Quadro> quadros = carregarTodos();
        quadros = quadros.stream()
                .filter(q -> !q.getId().equals(quadro.getId()))
                .collect(Collectors.toList());
        quadros.add(quadro);

        salvarTodos(quadros);
        return quadro;
    }

    // Método para carregar todos os quadros do arquivo
    public List<Quadro> carregarTodos() {
        List<Quadro> quadros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_QUADROS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4) {
                    Long id = Long.parseLong(partes[0]);
                    String titulo = partes[1];
                    Long idProprietario = Long.parseLong(partes[2]);
                    String tipo = partes[3];

                    Optional<Usuario> proprietarioOpt = usuarioDAO.buscarPorId(idProprietario);
                    if (proprietarioOpt.isPresent()) {
                        Quadro quadro;
                        if ("compartilhado".equalsIgnoreCase(tipo)) {
                            quadro = new QuadroCompartilhado(id, titulo, proprietarioOpt.get());
                            if (partes.length > 4) {
                                String[] usuariosCompartilhadosIds = partes[4].split(",");
                                for (String usuarioId : usuariosCompartilhadosIds) {
                                    Optional<Usuario> usuarioCompartilhado = usuarioDAO.buscarPorId(Long.parseLong(usuarioId));
                                    usuarioCompartilhado.ifPresent(u -> ((QuadroCompartilhado) quadro).compartilharComUsuario(u));
                                }
                            }
                        } else {
                            quadro = new Quadro(id, titulo, proprietarioOpt.get());
                        }
                        quadros.add(quadro);
                    }
                }
            }
        } catch (IOException e) {
            // O arquivo pode não existir na primeira execução
        }
        return quadros;
    }

    public Optional<Quadro> buscarPorId(Long id) {
        return carregarTodos().stream()
                .filter(quadro -> quadro.getId().equals(id))
                .findFirst();
    }
    
    // Método para buscar quadros por usuário
    public List<Quadro> buscarQuadrosPorUsuario(Usuario usuario) {
        return carregarTodos().stream()
            .filter(quadro -> quadro.getProprietario().getId().equals(usuario.getId()))
            .collect(Collectors.toList());
    }

    public Quadro atualizar(Quadro quadroAtualizado) {
        List<Quadro> quadros = carregarTodos();
        quadros = quadros.stream()
                .map(q -> q.getId().equals(quadroAtualizado.getId()) ? quadroAtualizado : q)
                .collect(Collectors.toList());
        salvarTodos(quadros);
        return quadroAtualizado;
    }

    public void deletar(Long id) {
        List<Quadro> quadros = carregarTodos();
        quadros.removeIf(quadro -> quadro.getId().equals(id));
        salvarTodos(quadros);
    }
    
    private void salvarTodos(List<Quadro> quadros) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_QUADROS))) {
            for (Quadro quadro : quadros) {
                StringBuilder linha = new StringBuilder();
                linha.append(quadro.getId()).append(";")
                     .append(quadro.getTitulo()).append(";")
                     .append(quadro.getProprietario().getId()).append(";");

                if (quadro instanceof QuadroCompartilhado) {
                    linha.append("compartilhado");
                    List<Long> idsCompartilhados = ((QuadroCompartilhado) quadro).getUsuariosCompartilhados().stream()
                            .map(Usuario::getId)
                            .collect(Collectors.toList());
                    if (!idsCompartilhados.isEmpty()) {
                        linha.append(";")
                             .append(idsCompartilhados.stream().map(String::valueOf).collect(Collectors.joining(",")));
                    }
                } else {
                    linha.append("normal");
                }
                writer.write(linha.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}