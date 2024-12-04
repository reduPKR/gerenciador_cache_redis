package br.com.gerenciador.controller;

import br.com.gerenciador.dto.ListaTarefaResponse;
import br.com.gerenciador.dto.TarefaResponse;
import br.com.gerenciador.dto.UsuarioRequestDto;
import br.com.gerenciador.dto.UsuarioResponseDto;
import br.com.gerenciador.mapper.UsuarioModelToResponse;
import br.com.gerenciador.mapper.UsuarioRequestToModel;
import br.com.gerenciador.model.UsuarioModel;
import br.com.gerenciador.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
   private final UsuarioService service;
   private final UsuarioRequestToModel usuarioRequestToModel;
   private final UsuarioModelToResponse usuarioModelToResponse;

   @Autowired
   public UsuarioController(UsuarioService service,
                            UsuarioRequestToModel usuarioRequestToModel,
                            UsuarioModelToResponse usuarioModelToResponse) {
      this.service = service;
      this.usuarioRequestToModel = usuarioRequestToModel;
      this.usuarioModelToResponse = usuarioModelToResponse;
   }

   @PostMapping("/registrar")
   public ResponseEntity<UsuarioResponseDto> salvar(@Validated @RequestBody UsuarioRequestDto request){
      if(service.validar(request.username()))
         return new ResponseEntity<>(
                 new UsuarioResponseDto(
                         null,
                         request.username(),
                         request.nome(),
                         request.tipo())
                 , HttpStatus.BAD_REQUEST);

      UsuarioModel model = usuarioRequestToModel.executar(request);

      service.salvar(model);

      UsuarioResponseDto response = usuarioModelToResponse.executar(model);

      if (response.id() != null){
         return new ResponseEntity<>(response, HttpStatus.CREATED);
      }
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @GetMapping("/{uuid}")
   public ResponseEntity<UsuarioResponseDto> buscar(@PathVariable UUID uuid){
      UsuarioModel model = service.buscar(uuid);

      if (model != null){
         UsuarioModelToResponse usuarioModelToResponse = new UsuarioModelToResponse();
         UsuarioResponseDto response = usuarioModelToResponse.executar(model);

         return new ResponseEntity<>(response, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

   @GetMapping("/{uuid}/tarefas")
   public  ResponseEntity<ListaTarefaResponse> buscarTarefa(@PathVariable UUID uuid){
      Set<TarefaResponse> lista = service.buscarTarefas(uuid);

      if (!lista.isEmpty()){
         ListaTarefaResponse response = new ListaTarefaResponse(lista);
         return new ResponseEntity<>(response, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

   @GetMapping
   public ResponseEntity<Page<UsuarioResponseDto>> findAll(
           @RequestParam(value = "page", defaultValue = "0") Integer page,
           @RequestParam(value = "size", defaultValue = "10") Integer size,
           @RequestParam(value = "direction", defaultValue = "asc") String direction
   ){
      var direcao = pegarDirecao(direction);

      Pageable pageable = PageRequest.of(page, size, direcao);

      Page<UsuarioModel>  usuarioModel = service.getPaginado(pageable);

      if (!usuarioModel.isEmpty()) {
         Page<UsuarioResponseDto> response = usuarioModel.map(usuarioModelToResponse::executar);
         return new ResponseEntity<>(response, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

   private Sort pegarDirecao(String direction) {
      var sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
      return Sort.by(sortDirection, "nome");
   }

   @GetMapping("/filtro")
   public ResponseEntity<List<UsuarioResponseDto>> getUsersByFilter(@RequestParam("nome") String nome) {
      List<UsuarioModel> usuarios = service.getUsersByNome(nome);

      if (!usuarios.isEmpty()) {
         List<UsuarioResponseDto> response = usuarios.stream()
                 .map(usuarioModelToResponse::executar)
                 .toList();
         return new ResponseEntity<>(response, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
}
