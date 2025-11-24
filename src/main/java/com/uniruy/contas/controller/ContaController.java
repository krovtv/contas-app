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

import com.uniruy.contas.model.Conta;
import com.uniruy.contas.service.ContaService;


@Controller

public class ContaController {
    private final ContaService service;
    public ContaController(ContaService service){this.service = service;}

    @GetMapping("/contas")
    
    public String list(Model m){
        List<Conta> contas = service.findAll();
        m.addAttribute("contas", contas);
        m.addAttribute("saldo", service.calcularSaldo());
        return "contas/list";
    }

    @GetMapping("/novo")
    public String novoForm(Model m){
        m.addAttribute("conta", new Conta());
        return "contas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Conta conta){
        service.save(conta);
        return "redirect:/contas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model m){
        m.addAttribute("conta", service.findById(id));
        return "contas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id){
        service.delete(id);
        return "redirect:/contas";
    }

    @GetMapping("/vencendo")
    public String vencendo(Model m){
        LocalDate hoje = LocalDate.now();
        LocalDate fim = hoje.plusDays(5);
        m.addAttribute("contas", service.proximasVencendo(hoje, fim));
        return "contas/list";
    }
}
