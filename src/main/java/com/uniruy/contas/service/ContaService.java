package com.uniruy.contas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.uniruy.contas.model.Conta;
import com.uniruy.contas.repository.ContaRepository;

@Service
public class ContaService {
    private final ContaRepository repo;
    public ContaService(ContaRepository repo){this.repo = repo;}

    public Conta save(Conta c){
        if(c.getValor() == null || c.getValor() < 0) throw new IllegalArgumentException("Valor inválido");
        if(c.getStatus() == null) c.setStatus("PENDENTE");
        return repo.save(c);
    }
    public List<Conta> findAll(){return repo.findAll();}
    public Conta findById(Long id){return repo.findById(id).orElse(null);}
    public void delete(Long id){repo.deleteById(id);}
    public double calcularSaldo(){
        double receitas = repo.findAll().stream()
                .filter(c -> "RECEBER".equalsIgnoreCase(c.getTipo()) && "PAGO".equalsIgnoreCase(c.getStatus()))
                .mapToDouble(c -> c.getValor()).sum();
        double despesas = repo.findAll().stream()
                .filter(c -> "PAGAR".equalsIgnoreCase(c.getTipo()) && "PAGO".equalsIgnoreCase(c.getStatus()))
                .mapToDouble(c -> c.getValor()).sum();
        return receitas - despesas;
    }
    public List<Conta> proximasVencendo(LocalDate start, LocalDate end){
        return repo.findByDataVencimentoBetween(start, end);
    }
}
