package br.com.gerenciador.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class TarefaResponse implements Serializable {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate abertura;
    private int horasNescessarias;
    private int horasUsadas;

    public TarefaResponse() {
    }

    public TarefaResponse(Long id, String nome, String descricao, LocalDate abertura, int horasNescessarias, int horasUsadas) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.abertura = abertura;
        this.horasNescessarias = horasNescessarias;
        this.horasUsadas = horasUsadas;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getAbertura() {
        return abertura;
    }

    public int getHorasNescessarias() {
        return horasNescessarias;
    }

    public int getHorasUsadas() {
        return horasUsadas;
    }
}
