package br.com.gerenciador.dto;

import java.util.Set;

public record ListaTarefaResponse(Set<TarefaResponse> tarefas) {
}
