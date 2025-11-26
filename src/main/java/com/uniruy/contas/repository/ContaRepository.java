package com.uniruy.contas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uniruy.contas.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    // Buscar todas as contas do usuário
    List<Conta> findAllByUsuarioEmail(String email);

    // Contadores gerais
    long countByUsuarioEmail(String email);

    long countByUsuarioEmailAndStatus(String email, Conta.Status status);

    @Query("SELECT COUNT(c) FROM Conta c " +
           "WHERE c.usuario.email = :email " +
           "AND c.dataVencimento < :hoje " +
           "AND c.status = 'PENDENTE'")
    long countContasVencidas(String email, LocalDate hoje);

    // Totais financeiros
    @Query("SELECT SUM(c.valor) FROM Conta c " +
           "WHERE c.usuario.email = :email " +
           "AND c.tipo = 'PAGAR' " +
           "AND c.status = 'PENDENTE'")
    Double sumTotalAPagar(String email);

    @Query("SELECT SUM(c.valor) FROM Conta c " +
           "WHERE c.usuario.email = :email " +
           "AND c.tipo = 'RECEBER' " +
           "AND c.status = 'PENDENTE'")
    Double sumTotalAReceber(String email);

    // Próximos vencimentos
    List<Conta> findByUsuarioEmailAndDataVencimentoBetween(
            String email, LocalDate inicio, LocalDate fim
    );
}
