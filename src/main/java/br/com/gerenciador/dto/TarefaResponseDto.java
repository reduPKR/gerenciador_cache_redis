package br.com.gerenciador.dto;

import br.com.gerenciador.enums.StatusTarefa;

import java.time.LocalDate;

public record TarefaResponseDto(
        Long id,
        String nome,
        String nomeUsuario,
        String descricao,
        LocalDate abertura,
        StatusTarefa status,
        int horasNescessarias,
        int horasUsadas
) {
}
