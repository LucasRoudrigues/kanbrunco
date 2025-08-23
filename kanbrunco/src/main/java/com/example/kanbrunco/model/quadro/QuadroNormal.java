package com.example.kanbrunco.model.quadro;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("normal")
public class QuadroNormal extends Quadro {

    public QuadroNormal() {}
}