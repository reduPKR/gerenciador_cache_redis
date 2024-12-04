package br.com.gerenciador.controller;

import br.com.gerenciador.dto.AuthenticationRequest;
import br.com.gerenciador.dto.AuthenticationResponse;
import br.com.gerenciador.model.UsuarioModel;
import br.com.gerenciador.service.UsuarioService;
import br.com.gerenciador.service.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest request){
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UsuarioModel) auth.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
