package com.example.kanbrunco.controller;

import com.example.kanbrunco.model.cartao.*;
import com.example.kanbrunco.model.lista.ListaTarefas;
import com.example.kanbrunco.model.quadro.Quadro;
import com.example.kanbrunco.service.CartaoService;
import com.example.kanbrunco.service.ListaTarefasService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cartoes")
public class CartaoController {

	private final CartaoService cartaoService;
    private final ListaTarefasService listaService;

    public CartaoController(CartaoService cartaoService, ListaTarefasService listaService) {
        this.cartaoService = cartaoService;
        this.listaService = listaService;
    }

    // Listar todos os cartões existentes da lista
    @GetMapping("/lista/{listaId}")
    public String listarCartoesPorLista(@PathVariable Long listaId, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            return "redirect:/";
        }

        // Verifica quais cartões pertencem a lista
        Optional<ListaTarefas> listaOpt = listaService.buscarPorId(listaId);
        if (listaOpt.isPresent()) {
            ListaTarefas lista = listaOpt.get();
            List<Cartao> cartoes = cartaoService.buscarPorLista(lista);
            model.addAttribute("cartoes", cartoes);
            model.addAttribute("lista", lista);
            model.addAttribute("nivel", "cartao");
            return "cartoes/lista";
        }
        return "redirect:/listas";
    }

    //Metodo para criar os cartões simples por meio da factory method
    @PostMapping("/criarSimples/{listaId}")
    public String criarCartaoSimples(@PathVariable Long listaId,
                                    @RequestParam String titulo, 
                                    @RequestParam String descricao) {
        Optional<ListaTarefas> listaOpt = listaService.buscarPorId(listaId);
        if (listaOpt.isPresent()) {
            Cartao cartao = cartaoService.salvarCartaoSimples(titulo, descricao);
            ListaTarefas lista = listaOpt.get();
            lista.adicionarCartao(cartao);
            listaService.salvarLista(lista);
        }
        return "redirect:/cartoes/lista/" + listaId;
    }

    // Metodo para criar os cartões com checklist por meio do factory method
    @PostMapping("/criarComChecklist/{listaId}")
    public String criarCartaoComChecklist(@PathVariable Long listaId,
                                          @RequestParam String titulo,
                                          @RequestParam String descricao) {
        Optional<ListaTarefas> listaOpt = listaService.buscarPorId(listaId);
        if (listaOpt.isPresent()) {
            Cartao cartao = cartaoService.salvarCartaoComChecklist(titulo, descricao);
            ListaTarefas lista = listaOpt.get();
            lista.adicionarCartao(cartao);
            listaService.salvarLista(lista);
            return "redirect:/cartoes/lista/" + listaId;
        }
        return "redirect:/cartoes";
    }

    // Metodo para atualizar um cartão
    @PostMapping("/atualizar")
    public String atualizarCartao(@RequestParam Long id,
                                  @RequestParam String titulo,
                                  @RequestParam String descricao, @RequestParam Long listaId) {
        cartaoService.buscarPorId(id).ifPresent(cartao -> {
            cartao.setTitulo(titulo);
            cartao.setDescricao(descricao);
            cartaoService.salvarCartao(cartao); 
        });
        return "redirect:/cartoes/lista/" + listaId;
    }

    // Metodo para deletar um cartão
    @PostMapping("/deletar")
    public String deletarCartao(@RequestParam Long id, @RequestParam Long listaId) {
        cartaoService.deletarCartao(id);
        return "redirect:/cartoes/lista/" + listaId;
    }

    // Adiconar item de Checklist
    @PostMapping("/checklist/adicionarItem")
    public String adicionarItemChecklist(@RequestParam Long cartaoId,
                                         @RequestParam String item) {
        Optional<Cartao> cartaoOpt = cartaoService.buscarPorId(cartaoId);
        if (cartaoOpt.isPresent()) {
            Cartao cartao = cartaoOpt.get();
            if (cartao instanceof CartaoComChecklist) {
                CartaoComChecklist checklistCartao = (CartaoComChecklist) cartao;
                checklistCartao.adicionarItemChecklist(item);
                cartaoService.salvarCartao(checklistCartao);
                return "redirect:/cartoes/lista/" + cartao.getLista().getId();
            }
        }
        return "redirect:/cartoes";
    }

    // Remover item de checklist de um cartão
    @PostMapping("/checklist/removerItem")
    public String removerItemChecklist(@RequestParam Long cartaoId,
                                       @RequestParam int index) {
        Optional<Cartao> cartaoOpt = cartaoService.buscarPorId(cartaoId);
        if (cartaoOpt.isPresent()) {
            Cartao cartao = cartaoOpt.get();
            if (cartao instanceof CartaoComChecklist) {
                CartaoComChecklist checklistCartao = (CartaoComChecklist) cartao;
                if (index >= 0 && index < checklistCartao.getChecklist().size()) {
                    checklistCartao.getChecklist().remove(index);
                    cartaoService.salvarCartao(checklistCartao);
                    return "redirect:/cartoes/lista/" + cartao.getLista().getId();
                }
            }
        }
        return "redirect:/cartoes";
    }

    // Marcar item de checklist de um cartão
    @PostMapping("/checklist/marcarItem")
    public String marcarItemChecklist(@RequestParam Long cartaoId,
                                      @RequestParam int index) {
        Optional<Cartao> cartaoOpt = cartaoService.buscarPorId(cartaoId);
        if (cartaoOpt.isPresent()) {
            Cartao cartao = cartaoOpt.get();
            if (cartao instanceof CartaoComChecklist) {
                CartaoComChecklist checklistCartao = (CartaoComChecklist) cartao;
                if (index >= 0 && index < checklistCartao.getChecklist().size()) {
                    String item = checklistCartao.getChecklist().get(index);
                    checklistCartao.getChecklist().set(index, "[X] " + item);
                    cartaoService.salvarCartao(checklistCartao);
                    return "redirect:/cartoes/lista/" + cartao.getLista().getId();
                }
            }
        }
        return "redirect:/cartoes";
    }
}