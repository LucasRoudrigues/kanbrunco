package com.example.kanbrunco.model.lista;

import java.util.ArrayList;
import java.util.List;
import com.example.kanbrunco.model.cartao.Cartao;

// Esta classe representa uma lista de tarefas dentro de um quadro.
public class ListaTarefas {

    private String id;
    private String titulo;
    private List<Cartao> cartoes;

    public ListaTarefas(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.cartoes = new ArrayList<>();
    }
    
    // Método para adicionar um cartão à lista
    public void adicionarCartao(Cartao cartao) {
        this.cartoes.add(cartao);
    }
    
    // Método para remover um cartão da lista
    public void removerCartao(Cartao cartao) {
        this.cartoes.remove(cartao);
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

    public List<Cartao> getCartoes() {
        return cartoes;
    }
}
