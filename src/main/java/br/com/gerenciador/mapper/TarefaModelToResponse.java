package br.com.gerenciador.mapper;

import br.com.gerenciador.dto.TarefaResponseDto;
import br.com.gerenciador.model.TarefaModel;
import org.springframework.stereotype.Component;

@Component
public class TarefaModelToResponse implements Mapper<TarefaResponseDto, TarefaModel> {
    @Override
    public TarefaResponseDto executar(TarefaModel request) {
        return new TarefaResponseDto(
                request.getId(),
                request.getNome(),
                request.getNomeUsuario(),
                request.getDescricao(),
                request.getAbertura(),
                request.getStatus(),
                request.getHorasNescessarias(),
                request.getHorasUsadas()
        );
    }
}
