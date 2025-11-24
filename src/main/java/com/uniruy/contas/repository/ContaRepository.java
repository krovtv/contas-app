package com.uniruy.contas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniruy.contas.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByDataVencimentoBetween(LocalDate start, LocalDate end);
}
