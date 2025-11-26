package com.uniruy.contas.controller;

import com.uniruy.contas.service.ContaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    
    private final ContaService contaService;
    
    public DashboardController(ContaService contaService) {
        this.contaService = contaService;
    }
    
    @GetMapping
    public String dashboard(Model model, Authentication authentication) {
        String emailUsuario = authentication.getName();
        
        // Estatísticas gerais
        model.addAttribute("totalContas", contaService.contarTotal(emailUsuario));
        model.addAttribute("contasPendentes", contaService.contarPendentes(emailUsuario));
        model.addAttribute("contasPagas", contaService.contarPagas(emailUsuario));
        model.addAttribute("contasVencidas", contaService.contarVencidas(emailUsuario));
        
        // Valores
        model.addAttribute("totalAPagar", contaService.totalAPagar(emailUsuario));
        model.addAttribute("totalAReceber", contaService.totalAReceber(emailUsuario));
        model.addAttribute("saldoAtual", contaService.calcularSaldo(emailUsuario));
        
        // Contas próximas do vencimento (próximos 7 dias)
        LocalDate hoje = LocalDate.now();
        LocalDate daqui7Dias = hoje.plusDays(7);
        model.addAttribute("contasProximasVencimento", 
            contaService.buscarPorPeriodo(emailUsuario, hoje, daqui7Dias));
        
        return "dashboard/index";
    }
}