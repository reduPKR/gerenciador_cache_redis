package br.com.gerenciador.controller;

import br.com.gerenciador.dto.AtualizarHorasRequest;
import br.com.gerenciador.dto.AtualizarStatusRequest;
import br.com.gerenciador.mapper.TarefaModelToResponse;
import br.com.gerenciador.dto.TarefaRequestDto;
import br.com.gerenciador.dto.TarefaResponseDto;
import br.com.gerenciador.mapper.TarefaRequestToModel;
import br.com.gerenciador.model.TarefaModel;
import br.com.gerenciador.model.UsuarioModel;
import br.com.gerenciador.service.TarefaService;
import br.com.gerenciador.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {
    private final TarefaService tarefaService;
    private final UsuarioService usuarioService;
    private final TarefaRequestToModel tarefaRequestToModel;
    private final TarefaModelToResponse tarefaModelToResponse;

    @Autowired
    public TarefaController(TarefaService tarefaService,
                            UsuarioService usuarioService,
                            TarefaRequestToModel tarefaRequestToModel,
                            TarefaModelToResponse tarefaModelToResponse) {
        this.tarefaService = tarefaService;
        this.usuarioService = usuarioService;
        this.tarefaRequestToModel = tarefaRequestToModel;
        this.tarefaModelToResponse = tarefaModelToResponse;
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDto> salvar(@Validated @RequestBody TarefaRequestDto request){
        TarefaModel tarefaModel = tarefaRequestToModel.executar(request);

        UsuarioModel usuarioModel = usuarioService.buscar(request.usuarioId());
        if (usuarioModel != null) {
            tarefaModel.setUsuario(usuarioModel);
            tarefaService.salvar(tarefaModel);

            if (tarefaModel.getId() != 0) {
                usuarioModel.setTarefas(tarefaModel);
                usuarioService.salvar(usuarioModel);

                TarefaResponseDto response = tarefaModelToResponse.executar(tarefaModel);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TarefaResponseDto> deletar(@PathVariable Long id){
         TarefaModel tarefaModel = tarefaService.deletar(id);

         if (tarefaModel != null){
            TarefaModelToResponse tarefaModelToResponse = new TarefaModelToResponse();
            TarefaResponseDto response = tarefaModelToResponse.executar(tarefaModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
         }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping
    public ResponseEntity<TarefaResponseDto> atualizarHoras(@Validated @RequestBody AtualizarHorasRequest request){
        int qtdeHoras = Math.abs(request.qtdeHoras());
        TarefaModel tarefaModel = tarefaService.atualizaHoras(request.id(), qtdeHoras);

        if (tarefaModel != null){
            TarefaModelToResponse tarefaModelToResponse = new TarefaModelToResponse();
            TarefaResponseDto response = tarefaModelToResponse.executar(tarefaModel);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("/status")
    public ResponseEntity<TarefaResponseDto> atualizarStatus(@Validated @RequestBody AtualizarStatusRequest request){
        TarefaModel tarefaModel = tarefaService.atualizaStatus(request.id(), request.status());

        if (tarefaModel != null){
            TarefaModelToResponse tarefaModelToResponse = new TarefaModelToResponse();
            TarefaResponseDto response = tarefaModelToResponse.executar(tarefaModel);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
