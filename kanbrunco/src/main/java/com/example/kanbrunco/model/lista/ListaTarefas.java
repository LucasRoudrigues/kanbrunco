package com.example.kanbrunco.model.lista;

import java.util.ArrayList;
import java.util.List;
import com.example.kanbrunco.model.cartao.Cartao;

// Esta classe representa uma lista de tarefas dentro de um quadro.
public class ListaTarefas {

    private Long id;
    private Long quadroId;
    private String titulo;
    private List<Cartao> cartoes;

    // Construtor vazio para o DAO
    public ListaTarefas() {
    }
     
    public ListaTarefas(Long id, String titulo,long quadroId) {
        this.id = id;
        this.titulo = titulo;
        this.quadroId = quadroId;
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
    
    public Long getQuadroId() {
        return quadroId;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

	public void setQuadroId(long quadroId) {
		this.quadroId = quadroId;
		
	}
}
