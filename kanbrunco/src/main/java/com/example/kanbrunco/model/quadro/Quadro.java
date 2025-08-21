package com.example.kanbrunco.model.quadro;

import java.util.ArrayList;
import java.util.List;
import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.model.usuario.Usuario;

public class Quadro {

    private Long id;
    private String titulo;
    private Usuario proprietario;
    private List<ListaTarefas> listas;

    // Construtor vazio para o DAO
    public Quadro() {
        this.listas = new ArrayList<>();
    }

    // Construtor com argumentos para a lógica de negócio
    public Quadro(Long id, String titulo, Usuario proprietario) {
        this.id = id;
        this.titulo = titulo;
        this.proprietario = proprietario;
        this.listas = new ArrayList<>();
    }

    // Método para adicionar uma lista de tarefas ao quadro
    public void adicionarLista(ListaTarefas lista) {
        this.listas.add(lista);
    }

    // Método para remover uma lista de tarefas do quadro
    public void removerLista(ListaTarefas lista) {
        this.listas.remove(lista);
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getProprietario() {
        return proprietario;
    }

    // O DAO irá usar este setter para associar o objeto Usuario
    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }

    public List<ListaTarefas> getListas() {
        return listas;
    }
}