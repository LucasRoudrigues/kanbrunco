package com.example.kanbrunco.factory;

import com.example.kanbrunco.model.quadro.QuadroCompartilhado;
import com.example.kanbrunco.model.quadro.Quadro;

public class QuadroCompartilhadoFactory implements QuadroFactory {
    @Override
    public Quadro criarQuadro() {
        return new QuadroCompartilhado();
    }
}
