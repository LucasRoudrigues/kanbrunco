package com.example.kanbrunco.model.cartao;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("simples")
public class CartaoSimples extends Cartao {
    
    public CartaoSimples() {
        super();
    }
}
