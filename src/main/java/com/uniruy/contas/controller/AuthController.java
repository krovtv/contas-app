package com.uniruy.contas.controller;

import com.uniruy.contas.model.Usuario;
import com.uniruy.contas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    private final UsuarioService usuarioService;
    
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "erro", required = false) String erro,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        
        if (erro != null) {
            model.addAttribute("mensagem", "Email ou senha inválidos!");
            model.addAttribute("tipoMensagem", "danger");
        }
        
        if (logout != null) {
            model.addAttribute("mensagem", "Logout realizado com sucesso!");
            model.addAttribute("tipoMensagem", "success");
        }
        
        return "auth/login";
    }
    
    @GetMapping("/registro")
    public String registroPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }
    
    @PostMapping("/registro")
    public String registrar(
            @Valid @ModelAttribute Usuario usuario,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "auth/registro";
        }
        
        try {
            usuarioService.cadastrar(usuario);
            redirectAttributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso! Faça login.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/registro";
        }
    }
    
    @GetMapping("/403")
    public String acessoNegado() {
        return "error/403";
    }
}