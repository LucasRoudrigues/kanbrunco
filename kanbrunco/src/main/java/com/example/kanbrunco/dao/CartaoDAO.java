package com.example.kanbrunco.dao;

import com.example.kanbrunco.model.cartao.Cartao;
import com.example.kanbrunco.model.cartao.CartaoComChecklist;
import com.example.kanbrunco.model.cartao.CartaoSimples;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.model.usuario.UsuarioAdmin;
import com.example.kanbrunco.model.usuario.UsuarioBasico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartaoDAO {

    private static final String ARQUIVO_CARTOES = "cartoes.txt";
    private Long proximoId;

    public CartaoDAO() {
        this.proximoId = determinarProximoId();
    }

    private Long determinarProximoId() {
        return carregarTodos().stream()
                .mapToLong(cartao -> cartao.getId())
                .max()
                .orElse(0L) + 1;
    }
    
    // Método para salvar um novo cartao
    public Cartao salvar(Cartao cartao) {
        
        //Analisar cartoes com mesmo titulo somente para um usuario
        
        
        if (cartao.getId() == null) {
            cartao.setId(proximoId++);
        }
        
        List<Cartao> cartoes = carregarTodos();
        cartoes = cartoes.stream()
                .filter(u -> !u.getId().equals(cartao.getId()))
                .collect(Collectors.toList());
        cartoes.add(cartao);

        salvarTodos(cartoes);
        return cartao;
    }

    // Método para buscar todos os cartoes
    public List<Cartao> carregarTodos() {
        List<Cartao> cartoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CARTOES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4) {
                    String tipo = partes[0];
                    Long id = Long.parseLong(partes[1]);
                    String titulo = partes[2];
                    String descricao = partes[3];

                    Cartao cartao;
                    if ("simples".equalsIgnoreCase(tipo)) { // Verifica o tipo correto
                        cartao = new CartaoSimples();
                    } else if ("checklist".equalsIgnoreCase(tipo)) {
                        cartao = new CartaoComChecklist();
                    } else {
                        continue;
                    }

                    cartao.setId(id);
                    cartao.setTitulo(titulo);
                    cartao.setDescricao(descricao);
                    cartoes.add(cartao);
                }
            }
        } catch (IOException e) {
            // O arquivo pode não existir na primeira execução
        }
        return cartoes;
    }
    
    // Métodos de busca e deleção continuam iguais
    public Optional<Cartao> buscarPorId(Long id) {
        return carregarTodos().stream()
                .filter(cartao -> cartao.getId().equals(id))
                .findFirst();
    }

    public Optional<Cartao> buscarPorTitulo(String titulo) {
        return carregarTodos().stream()
                .filter(cartao -> cartao.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    public Cartao atualizar(Cartao cartaoAtualizado) {
        List<Cartao> cartoes = carregarTodos();
        cartoes = cartoes.stream()
                .map(u -> u.getId().equals(cartaoAtualizado.getId()) ? cartaoAtualizado : u)
                .collect(Collectors.toList());
        salvarTodos(cartoes);
        return cartaoAtualizado;
    }

    public void deletar(Long id) {
        List<Cartao> cartoes = carregarTodos();
        cartoes.removeIf(cartao -> cartao.getId().equals(id));
        salvarTodos(cartoes);
    }
    
    // Método auxiliar para reescrever todos os cartoes no arquivo
    private void salvarTodos(List<Cartao> cartoes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CARTOES))) {
            for (Cartao cartao : cartoes) {
                String tipo = cartao instanceof CartaoComChecklist ? "checklist" : "basico"; // Verifica o tipo correto
                String linha = tipo + ";" + cartao.getId() + ";" + cartao.getTitulo() + ";" + cartao.getDescricao() + "\n";
                writer.write(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
