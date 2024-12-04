package br.com.gerenciador.repository;

import br.com.gerenciador.model.TarefaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository  extends JpaRepository<TarefaModel, Long> {
}
