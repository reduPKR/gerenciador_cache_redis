package br.com.gerenciador.service;

import br.com.gerenciador.enums.StatusTarefa;
import br.com.gerenciador.model.TarefaModel;
import br.com.gerenciador.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {
    private final TarefaRepository repository;

    @Autowired
    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void salvar(TarefaModel model){
        this.repository.save(model);
    }

    public TarefaModel deletar(Long id) {
        //this.repository.deleteById(id);
        TarefaModel tarefa = this.repository.findById(id).orElse(null);

        if (tarefa != null){
            this.repository.delete(tarefa);
        }
        return tarefa;
    }

    @Transactional
    public TarefaModel atualizaHoras(Long id, int qtdeHoras) {
        TarefaModel tarefa = this.repository.findById(id).orElse(null);

        if (tarefa != null){
            tarefa.incrementarHorasUsadas(qtdeHoras);
            this.repository.save(tarefa);
        }

        return tarefa;
    }

    public TarefaModel atualizaStatus(Long id, StatusTarefa status) {
        if (status != StatusTarefa.TO_DO){
            TarefaModel tarefa = this.repository.findById(id).orElse(null);

            if (tarefa != null){
                tarefa.setStatus(status);
                this.repository.save(tarefa);
                return tarefa;
            }
        }
        return null;
    }
}
