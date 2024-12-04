package br.com.gerenciador.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AtualizarHorasRequest(
        @NotNull
        Long id,
        @NotNull
        @Min(1)
        int qtdeHoras
) {
}
