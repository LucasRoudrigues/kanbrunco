package com.example.kanbrunco.controller;

import com.example.kanbrunco.model.cartao.*;
import com.example.kanbrunco.service.CartaoService; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cartoes")
public class CartaoController {

	private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    // Listar todos os cartões existentes
    @GetMapping
    public String listarCartoes(Model model) {
        List<Cartao> cartoes = cartaoService.listarTodos(); // Usa o service
        model.addAttribute("cartoes", cartoes);
        return "cartoes/lista";
    }

    //Métodos para criar os cartões(simples e comChecklist)
    @PostMapping("/criarSimples")
    public String criarCartaoSimples(@RequestParam String titulo, @RequestParam String descricao) {
        cartaoService.salvarCartaoSimples(titulo, descricao);
        return "redirect:/cartoes";
    }

    @PostMapping("/criarComChecklist")
    public String criarCartaoComChecklist(@RequestParam String titulo, @RequestParam String descricao) {
        cartaoService.salvarCartaoComChecklist(titulo, descricao);
        return "redirect:/cartoes";
    }


    // Atualizar um cartão
    @PostMapping("/atualizar")
    public String atualizarCartao(@RequestParam Long id,
                                  @RequestParam String titulo,
                                  @RequestParam String descricao) {
        cartaoService.buscarPorId(id).ifPresent(cartao -> {
            cartao.setTitulo(titulo);
            cartao.setDescricao(descricao);
            cartaoService.salvarCartao(cartao); // Usa o service
        });
        return "redirect:/cartoes";
    }

    // Deletar um cartão
    @PostMapping("/deletar")
    public String deletarCartao(@RequestParam Long id) {
        cartaoService.deletarCartao(id); // Usa o service
        return "redirect:/cartoes";
    }
    
}

   
