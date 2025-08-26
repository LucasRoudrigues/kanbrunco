package com.example.kanbrunco.factory;

import com.example.kanbrunco.model.quadro.QuadroNormal;
import com.example.kanbrunco.model.quadro.Quadro;

public class QuadroNormalFactory implements QuadroFactory {
    @Override
    public Quadro criarQuadro() {
        return new QuadroNormal();
    }
}
