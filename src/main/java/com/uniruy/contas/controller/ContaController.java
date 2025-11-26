package com.uniruy.contas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniruy.contas.model.Conta;
import com.uniruy.contas.model.Usuario;
import com.uniruy.contas.service.ContaService;
import com.uniruy.contas.service.UsuarioService;

@Controller
@RequestMapping("/contas")
public class ContaController {

    private final ContaService service;
    private final UsuarioService usuarioService;

    public ContaController(ContaService service, UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    // LISTAGEM -------------------------------------
    @GetMapping
    public String list(Model model, Authentication auth) {

        String email = auth.getName();

        List<Conta> contas = service.findAllByUsuarioEmail(email);

        model.addAttribute("contas", contas);
        model.addAttribute("saldo", service.calcularSaldo(email));

        // 🔥 ADICIONADO: envia nome do usuário logado para a tela
        Usuario usuario = usuarioService.buscarPorEmail(email).orElse(null);
        if (usuario != null) {
            model.addAttribute("usuarioLogado", usuario.getNome());
        }

        return "contas/list";
    }

    // NOVO -----------------------------------------
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("conta", new Conta());
        return "contas/form";
    }

    // SALVAR ----------------------------------------
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Conta conta,
                         Authentication auth,
                         RedirectAttributes ra) {

        try {
            Usuario usuario = usuarioService.buscarPorEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            conta.setUsuario(usuario);

            service.save(conta);

            ra.addFlashAttribute("mensagem", "Conta salva com sucesso!");
            ra.addFlashAttribute("tipoMensagem", "success");

        } catch (Exception e) {
            ra.addFlashAttribute("mensagem", "Erro ao salvar conta: " + e.getMessage());
            ra.addFlashAttribute("tipoMensagem", "danger");
            e.printStackTrace();
        }

        return "redirect:/contas";
    }

    // EDITAR ---------------------------------------
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                         Model model,
                         RedirectAttributes ra,
                         Authentication auth) {

        Conta conta = service.findById(id);

        if (conta == null || !conta.getUsuario().getEmail().equals(auth.getName())) {
            ra.addFlashAttribute("mensagem", "Conta não encontrada!");
            ra.addFlashAttribute("tipoMensagem", "warning");
            return "redirect:/contas";
        }

        model.addAttribute("conta", conta);
        return "contas/form";
    }

    // EXCLUIR -----------------------------------------
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id,
                          RedirectAttributes ra,
                          Authentication auth) {

        Conta conta = service.findById(id);

        if (conta == null || !conta.getUsuario().getEmail().equals(auth.getName())) {
            ra.addFlashAttribute("mensagem", "Conta não encontrada ou não pertence a você!");
            ra.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/contas";
        }

        try {
            service.delete(id);
            ra.addFlashAttribute("mensagem", "Conta excluída com sucesso!");
            ra.addFlashAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            ra.addFlashAttribute("mensagem", "Erro ao excluir: " + e.getMessage());
            ra.addFlashAttribute("tipoMensagem", "danger");
        }

        return "redirect:/contas";
    }

    // PRÓXIMOS 5 DIAS ---------------------------------
    @GetMapping("/vencendo")
    public String vencendo(Model model, Authentication auth) {

        String email = auth.getName();

        LocalDate hoje = LocalDate.now();
        LocalDate fim = hoje.plusDays(5);

        List<Conta> contas = service.buscarPorPeriodo(email, hoje, fim);

        model.addAttribute("contas", contas);
        model.addAttribute("saldo", service.calcularSaldo(email));
        model.addAttribute("filtroAtivo", "Contas vencendo nos próximos 5 dias");

        // 🔥 também envia o usuário aqui
        Usuario usuario = usuarioService.buscarPorEmail(email).orElse(null);
        if (usuario != null) {
            model.addAttribute("usuarioLogado", usuario.getNome());
        }

        return "contas/list";
    }
}
