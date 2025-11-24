package com.uniruy.contas.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Conta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo; // PAGAR ou RECEBER
    private String descricao;
    private Double valor;
    private LocalDate dataVencimento;
    private String status; // PENDENTE, PAGO, ATRASADO

    // getters e setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public String getTipo(){return tipo;}
    public void setTipo(String tipo){this.tipo = tipo;}
    public String getDescricao(){return descricao;}
    public void setDescricao(String descricao){this.descricao = descricao;}
    public Double getValor(){return valor;}
    public void setValor(Double valor){this.valor = valor;}
    public LocalDate getDataVencimento(){return dataVencimento;}
    public void setDataVencimento(LocalDate dataVencimento){this.dataVencimento = dataVencimento;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}
}
