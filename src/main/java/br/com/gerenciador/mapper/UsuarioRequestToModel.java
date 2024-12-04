package br.com.gerenciador.mapper;

import br.com.gerenciador.dto.UsuarioRequestDto;
import br.com.gerenciador.model.UsuarioModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRequestToModel implements Mapper <UsuarioModel, UsuarioRequestDto>{
    @Override
    public UsuarioModel executar(UsuarioRequestDto request) {
        String password = new BCryptPasswordEncoder().encode(request.password());
        return new UsuarioModel(request.nome(), request.username(), password, request.tipo());
    }
}
