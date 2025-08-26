package com.example.kanbrunco.factory;

import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.model.usuario.UsuarioAdmin;

public class UsuarioAdminFactory implements UsuarioFactory {
    @Override
    public Usuario criarUsuario() {
        return new UsuarioAdmin();
    }
}