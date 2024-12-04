package br.com.gerenciador.dto;

import br.com.gerenciador.enums.TipoUsuario;

import java.util.UUID;

public record UsuarioResponseDto(
        UUID id,
        String username,
        String nome,
        TipoUsuario tipo
) {
}
