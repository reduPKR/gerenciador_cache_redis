package br.com.gerenciador.dto;

import br.com.gerenciador.enums.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDto(
        @NotNull
        @NotBlank
        String nome,
        @NotNull
        @NotBlank
        String username,
        @NotNull
        @NotBlank
        String password,
        @NotNull
        TipoUsuario tipo
) {
}
