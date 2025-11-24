package com.uniruy.contas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniruy.contas.model.Conta;
import com.uniruy.contas.service.ContaService;

@Controller
@RequestMapping("/contas") // ✅ Centraliza o prefixo /contas
public class ContaController {
    
    private final ContaService service;
    
    public ContaController(ContaService service) {
        this.service = service;
    }
    
    // ✅ CORRIGIDO: Agora é apenas @GetMapping
    @GetMapping
    public String list(Model model) {
        List<Conta> contas = service.findAll();
        model.addAttribute("contas", contas);
        model.addAttribute("saldo", service.calcularSaldo());
        return "contas/list";
    }
    
    // ✅ CORRIGIDO: Rota agora é /contas/novo
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("conta", new Conta());
        return "contas/form";
    }
    
    // ✅ CORRIGIDO: Rota agora é /contas/salvar + feedback ao usuário
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Conta conta, RedirectAttributes redirectAttributes) {
        try {
            service.save(conta);
            redirectAttributes.addFlashAttribute("mensagem", "Conta salva com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar conta: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
        }
        return "redirect:/contas";
    }
    
    // ✅ CORRIGIDO: Rota agora é /contas/editar/{id}
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Conta conta = service.findById(id);
        if (conta == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Conta não encontrada!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "warning");
            return "redirect:/contas";
        }
        model.addAttribute("conta", conta);
        return "contas/form";
    }
    
    // ✅ CORRIGIDO: Rota agora é /contas/excluir/{id} + feedback
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("mensagem", "Conta excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir conta: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
        }
        return "redirect:/contas";
    }
    
    // ✅ CORRIGIDO: Rota agora é /contas/vencendo
    @GetMapping("/vencendo")
    public String vencendo(Model model) {
        LocalDate hoje = LocalDate.now();
        LocalDate fim = hoje.plusDays(5);
        List<Conta> contas = service.proximasVencendo(hoje, fim);
        model.addAttribute("contas", contas);
        model.addAttribute("saldo", service.calcularSaldo());
        model.addAttribute("filtroAtivo", "Contas vencendo nos próximos 5 dias");
        return "contas/list";
    }
}