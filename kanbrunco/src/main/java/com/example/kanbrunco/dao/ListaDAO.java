package com.example.kanbrunco.dao;

import com.example.kanbrunco.model.lista.ListaTarefas;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListaDAO {

    private static final String ARQUIVO_LISTAS = "listas.txt";
    private Long proximoId;

    public ListaDAO() {
        this.proximoId = determinarProximoId();
    }
    
    private Long determinarProximoId() {
        return carregarTodos().stream()
                .mapToLong(ListaTarefas::getId)
                .max()
                .orElse(0L) + 1;
    }

    public ListaTarefas salvar(ListaTarefas lista) {
        if (lista.getId() == null) {
            lista.setId(proximoId++);
        }
        
        List<ListaTarefas> listas = carregarTodos();
        listas.add(lista);
        salvarTodos(listas);
        return lista;
    }

    public List<ListaTarefas> carregarTodos() {
        List<ListaTarefas> listas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_LISTAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    ListaTarefas lista = new ListaTarefas();
                    lista.setId(Long.parseLong(partes[0]));
                    lista.setQuadroId(Long.parseLong(partes[1]));
                    lista.setTitulo(partes[2]);
                    listas.add(lista);
                }
            }
        } catch (IOException e) {
            // Arquivo pode não existir
        }
        return listas;
    }
    
    public Optional<ListaTarefas> buscarPorId(Long id) {
        return carregarTodos().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }
    
    public void deletar(Long id) {
        List<ListaTarefas> listas = carregarTodos();
        listas.removeIf(l -> l.getId().equals(id));
        salvarTodos(listas);
    }
    
    private void salvarTodos(List<ListaTarefas> listas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_LISTAS))) {
            for (ListaTarefas lista : listas) {
                String linha = lista.getId() + ";" + lista.getQuadroId() + ";" + lista.getTitulo() + "\n";
                writer.write(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}