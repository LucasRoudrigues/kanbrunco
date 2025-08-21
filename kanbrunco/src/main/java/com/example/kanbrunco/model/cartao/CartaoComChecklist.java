package com.example.kanbrunco.model.cartao;

import java.util.ArrayList;
import java.util.List;

public class CartaoComChecklist extends Cartao {

    private List<String> checklist;
    
    public CartaoComChecklist(String id, String titulo, String descricao) {
        super(id, titulo, descricao);
        this.checklist = new ArrayList<>();
    }

    // Método para adicionar um item ao checklist.
    public void adicionarItemChecklist(String item) {
        this.checklist.add(item);
    }

    // Método para remover um item do checklist.
    public void removerItemChecklist(String item) {
        this.checklist.remove(item);
    }
    
    // Método sobrescrito para exibir detalhes com o checklist.
    @Override
    public void exibirDetalhes() {
        System.out.println("--- Cartão com Checklist ---");
        System.out.println("Título: " + getTitulo());
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Checklist:");
        for (String item : checklist) {
            System.out.println("- " + item);
        }
        System.out.println("Comentários (" + getComentarios().size() + ")");
    }
    
    public List<String> getChecklist() {
        return checklist;
    }
}

