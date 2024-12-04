package br.com.gerenciador.model;

import br.com.gerenciador.enums.StatusTarefa;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tarefa")
public class TarefaModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String nome;
    @NotBlank
    @NotNull
    private String descricao;
    @PastOrPresent
    private LocalDate abertura;
    @Min(1)
    @Max(100)
    private int horasNescessarias;
    @PositiveOrZero
    private int horasUsadas;
    @Enumerated(EnumType.STRING)
    private StatusTarefa status;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UsuarioModel usuario;

    public TarefaModel() {
    }

    public TarefaModel(String nome, String descricao, LocalDate abertura, int horasNescessarias) {
        this.nome = nome;
        this.descricao = descricao;
        this.abertura = abertura;
        this.horasNescessarias = horasNescessarias;
        this.horasUsadas = 0;
        this.status = StatusTarefa.TO_DO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNomeUsuario() {
        return usuario.getNome();
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public void incrementarHorasUsadas(int qtdeHoras) {
        this.horasUsadas += qtdeHoras;

        if (this.status != StatusTarefa.OVERDUE  && this.horasUsadas > this.horasNescessarias ) {
            this.status = StatusTarefa.OVERDUE;
        }else if (this.status != StatusTarefa.IN_PROGRESS && this.status != StatusTarefa.OVERDUE)
            this.status = StatusTarefa.IN_PROGRESS;

    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    public StatusTarefa getStatus() {
        return status;
    }
}
