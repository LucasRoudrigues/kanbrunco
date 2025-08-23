package com.example.kanbrunco.model.cartao;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("simples")
public class CartaoSimples extends Cartao {
    
    public CartaoSimples() {}
}