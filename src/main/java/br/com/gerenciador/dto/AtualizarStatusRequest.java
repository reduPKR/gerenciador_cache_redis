package br.com.gerenciador.dto;

import br.com.gerenciador.enums.StatusTarefa;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusRequest(
        @NotNull
        Long id,
        @NotNull
        StatusTarefa status) {
}
