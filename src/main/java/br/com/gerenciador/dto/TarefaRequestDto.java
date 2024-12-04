package br.com.gerenciador.dto;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public record TarefaRequestDto(
        @NotNull
        UUID usuarioId,
        @NotBlank
        @NotNull
        String nome,
        @NotBlank
        @NotNull
        String descricao,
        @PastOrPresent
        LocalDate abertura,
        @Min(1)
        @Max(100)
        int horasNescessarias
) implements Serializable {
}
