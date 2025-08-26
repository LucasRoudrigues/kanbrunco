package com.example.kanbrunco.factory;

import java.util.ArrayList;

import com.example.kanbrunco.model.cartao.Cartao;
import com.example.kanbrunco.model.cartao.CartaoComChecklist;
import com.example.kanbrunco.model.cartao.CartaoSimples;

public class CartaoFactory {

    public static Cartao criarCartao(String tipo) {
        switch (tipo.toLowerCase()) {
            case "simples":
                return new CartaoSimples();
            case "checklist":
                CartaoComChecklist cartaoComChecklist = new CartaoComChecklist();
                cartaoComChecklist.setChecklist(new ArrayList<>());
                return cartaoComChecklist;
            default:
                throw new IllegalArgumentException("Tipo de cartão inválido: " + tipo);
        }
    }
}