package com.example.kanbrunco.model.cartao;

import com.example.kanbrunco.model.cartao.Cartao;

public class CartaoSimples extends Cartao {

    public CartaoSimples(){
        super();
    }

    public CartaoSimples(Long id, String titulo, String descricao, Long idquadro) {
        super(id, titulo, descricao, idquadro);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("--- Cartão Simples ---");
        System.out.println("Título: " + getTitulo());
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Comentários (" + getComentarios().size() + ")");
    }
}
