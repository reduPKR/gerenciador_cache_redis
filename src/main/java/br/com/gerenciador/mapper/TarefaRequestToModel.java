package br.com.gerenciador.mapper;

import br.com.gerenciador.dto.TarefaRequestDto;
import br.com.gerenciador.model.TarefaModel;
import org.springframework.stereotype.Component;

@Component
public class TarefaRequestToModel implements Mapper<TarefaModel, TarefaRequestDto> {
    @Override
    public TarefaModel executar(TarefaRequestDto request) {
        return new TarefaModel(
            request.nome(),
            request.descricao(),
            request.abertura(),
            request.horasNescessarias()
        );
    }
}
