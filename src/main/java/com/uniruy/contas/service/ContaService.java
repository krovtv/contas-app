package com.uniruy.contas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.uniruy.contas.model.Conta;
import com.uniruy.contas.repository.ContaRepository;

@Service
public class ContaService {

    private final ContaRepository repo;

    public ContaService(ContaRepository repo) {
        this.repo = repo;
    }

    // ------------------------
    // CRUD
    // ------------------------

    public Conta save(Conta c) {
        if (c.getValor() == null || c.getValor().doubleValue() <= 0)
            throw new IllegalArgumentException("Valor inválido");

        if (c.getStatus() == null)
            c.setStatus(Conta.Status.PENDENTE);

        return repo.save(c);
    }

    public List<Conta> findAll() {
        return repo.findAll();
    }

    public Conta findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // ------------------------
    // DASHBOARD (Filtros por e-mail)
    // ------------------------

    public long contarTotal(String email) {
        return repo.countByUsuarioEmail(email);
    }

    public long contarPendentes(String email) {
        return repo.countByUsuarioEmailAndStatus(email, Conta.Status.PENDENTE);
    }

    public long contarPagas(String email) {
        return repo.countByUsuarioEmailAndStatus(email, Conta.Status.PAGO);
    }

    public long contarVencidas(String email) {
        return repo.countContasVencidas(email, LocalDate.now());
    }

    public double totalAPagar(String email) {
        return repo.sumTotalAPagar(email);
    }

    public double totalAReceber(String email) {
        return repo.sumTotalAReceber(email);
    }

    public double calcularSaldo(String email) {
        Double receber = repo.sumTotalAReceber(email);
        Double pagar = repo.sumTotalAPagar(email);
        return (receber != null ? receber : 0) - (pagar != null ? pagar : 0);
    }

    // ------------------------
    // LISTAR CONTAS DO USUÁRIO
    // ------------------------

    public List<Conta> findAllByUsuarioEmail(String email) {
        return repo.findAllByUsuarioEmail(email);
    }

    // ------------------------
    // PRÓXIMOS 7 DIAS
    // ------------------------

    public List<Conta> buscarPorPeriodo(String email, LocalDate inicio, LocalDate fim) {
        return repo.findByUsuarioEmailAndDataVencimentoBetween(email, inicio, fim);
    }
}
