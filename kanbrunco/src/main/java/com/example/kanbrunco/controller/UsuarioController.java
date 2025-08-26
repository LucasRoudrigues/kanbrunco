package com.example.kanbrunco.controller;

import com.example.kanbrunco.factory.UsuarioAdminFactory;
import com.example.kanbrunco.factory.UsuarioBasicoFactory;
import com.example.kanbrunco.model.usuario.*;
import com.example.kanbrunco.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listar todos os usuários
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("nivel", "usuario");
        return "usuarios/lista";
    }

    //Métodos para criação de usuarios pelo factory method
    @PostMapping("/criarBasico")
    public String criarUsuarioBasico(@RequestParam String nome, @RequestParam String email) {
        usuarioService.salvarUsuario(nome, email, new UsuarioBasicoFactory());
        return "redirect:/usuarios";
    }

    @PostMapping("/criarAdmin")
    public String criarUsuarioAdmin(@RequestParam String nome, @RequestParam String email) {
        usuarioService.salvarUsuario(nome, email, new UsuarioAdminFactory());
        return "redirect:/usuarios";
    }


    // Atualizar usuário
    @PostMapping("/atualizar")
    public String atualizarUsuario(@RequestParam Long id,
                                   @RequestParam String nome,
                                   @RequestParam String email) {
        Optional<Usuario> optionalUsuario = usuarioService.buscarPorId(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuarioService.salvarUsuario(usuario);
        }
        return "redirect:/usuarios";
    }

    // Deletar usuário
    @PostMapping("/deletar")
    public String deletarUsuario(@RequestParam Long id) {
        usuarioService.deletarUsuario(id);
        return "redirect:/usuarios";
    }
}