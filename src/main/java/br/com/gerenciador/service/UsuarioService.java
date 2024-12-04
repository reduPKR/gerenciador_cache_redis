package br.com.gerenciador.service;

import br.com.gerenciador.dto.TarefaResponse;
import br.com.gerenciador.model.TarefaModel;
import br.com.gerenciador.model.UsuarioModel;
import br.com.gerenciador.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UsuarioModel salvar(UsuarioModel model){
        this.repository.save(model);
        return model;
    }

    public UsuarioModel buscar(UUID uuid) {
        return repository.findById(uuid).orElse(null);
    }

    @Cacheable(value = "tarefas", key = "#id")
    public Set<TarefaResponse> buscarTarefas(UUID id){
        UsuarioModel usuario = repository.findById(id).orElse(null);

        if (usuario != null){
            Set<TarefaModel> lista = usuario.getTarefas();
            return lista
                    .stream()
                    .map(item -> new TarefaResponse(
                            item.getId(),
                            item.getNome(),
                            item.getDescricao(),
                            item.getAbertura(),
                            item.getHorasNescessarias(),
                            item.getHorasUsadas()
                    ))
                    .collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }

    public boolean validar(String username) {
        return repository.findByUsername(username) != null;
    }


    public Page<UsuarioModel> getPaginado(Pageable pageable) {
        //return repository.findAll(pageable); Não porem não considera maiuscula e minuscula iguais
        return repository.findByNomeIgnoreCase(pageable);
    }

    public List<UsuarioModel> getUsersByNome(String nome) {
        //return repository.findByNome(nome); vai buscar o nome exato
        //return repository.findByNomeContaining(nome); tras caso contenha em qualquer ordem
        return repository.findByNomeStartingWith(nome);
    }
}
