package com.example.kanbrunco.model.quadro;

import java.util.ArrayList;
import java.util.List;
import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.model.usuario.Usuario;

// Esta classe pode ser abstrata caso precise de métodos específicos implementados em subclasses
public class Quadro {

    private String id;
    private String titulo;
    private Usuario proprietario; // O usuário que criou o quadro
    private List<ListaTarefas> listas;

    public Quadro(String id, String titulo, Usuario proprietario) {
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }

    public List<ListaTarefas> getListas() {
        return listas;
    }
}
