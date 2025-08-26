package com.example.kanbrunco.factory;

import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.model.usuario.UsuarioBasico;

public class UsuarioBasicoFactory implements UsuarioFactory {
    @Override
    public Usuario criarUsuario() {
        return new UsuarioBasico();
    }
}