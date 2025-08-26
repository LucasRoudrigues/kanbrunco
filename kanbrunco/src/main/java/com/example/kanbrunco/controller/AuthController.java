package com.example.kanbrunco.controller;

import com.example.kanbrunco.factory.UsuarioAdminFactory;
import com.example.kanbrunco.factory.UsuarioBasicoFactory;
import com.example.kanbrunco.model.usuario.Usuario;
import com.example.kanbrunco.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // Apenas o email é usado para o login
    @PostMapping("/login")
    public String login(@RequestParam String email, HttpSession session, Model model) {
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);

        if (usuario.isPresent()) {
            session.setAttribute("currentUserId", usuario.get().getId());
            session.setAttribute("currentUserName", usuario.get().getNome());
            // Adicionando o tipo de usuário na sessão
            session.setAttribute("currentUserTipo", usuario.get().getTipo());
            // Redireciona diretamente para a página de quadros
            return "redirect:/quadros";
        }

        model.addAttribute("loginErro", "E-mail não encontrado. Cadastre-se.");
        return "auth/login";
    }

    // Metodo para registro de usuario
    @PostMapping("/registrar")
    public String registrar(@RequestParam String nome, @RequestParam String email,
                            @RequestParam String tipo, HttpSession session, Model model) {
        Optional<Usuario> existingUser = usuarioService.buscarPorEmail(email);
        // Verificação de email cadastrado
        if (existingUser.isPresent()) {
            model.addAttribute("cadastroMsg", "E-mail já cadastrado.");
            return "auth/login";
        }

        // Verifica o tipo de usuario cadastrado
        Usuario novoUsuario;
        if ("admin".equals(tipo)) {
            novoUsuario = usuarioService.salvarUsuario(nome, email, new UsuarioAdminFactory());
        } else {
            novoUsuario = usuarioService.salvarUsuario(nome, email, new UsuarioBasicoFactory());
        }

        session.setAttribute("currentUserId", novoUsuario.getId());
        session.setAttribute("currentUserName", novoUsuario.getNome());
        session.setAttribute("currentUserTipo", novoUsuario.getTipo());
        return "redirect:/quadros";
    }

    // Metodo para desconectar o usuario
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}