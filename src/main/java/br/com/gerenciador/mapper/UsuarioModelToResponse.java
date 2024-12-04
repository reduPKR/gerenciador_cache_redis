package br.com.gerenciador.mapper;

import br.com.gerenciador.dto.UsuarioResponseDto;
import br.com.gerenciador.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelToResponse implements Mapper <UsuarioResponseDto, UsuarioModel>{
    @Override
    public UsuarioResponseDto executar(UsuarioModel request) {
        return new UsuarioResponseDto(
                request.getId(),
                request.getUsername(),
                request.getNome(),
                request.getTipo()
        );
    }
}
