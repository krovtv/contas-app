package com.uniruy.contas.service;

import com.uniruy.contas.model.Usuario;
import com.uniruy.contas.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public Usuario salvar(Usuario usuario) {
        // Criptografa a senha antes de salvar
        if (usuario.getId() == null || !usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return repository.save(usuario);
    }
    
    @Transactional
    public Usuario cadastrar(Usuario usuario) {
        // Verifica se email já existe
        if (repository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }
        return salvar(usuario);
    }
    
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }
    
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }
    
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }
    
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
    
    @Transactional
    public void ativarDesativar(Long id) {
        Usuario usuario = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        usuario.setAtivo(!usuario.getAtivo());
        repository.save(usuario);
    }
    
    public boolean validarSenha(Usuario usuario, String senhaRaw) {
        return passwordEncoder.matches(senhaRaw, usuario.getSenha());
    }
}