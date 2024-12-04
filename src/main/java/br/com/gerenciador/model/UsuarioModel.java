package br.com.gerenciador.model;

import br.com.gerenciador.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "usuario")
public class UsuarioModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    @NotBlank
    private String nome;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private final Set<TarefaModel> tarefas;

    public UsuarioModel() {
        this.tarefas = new HashSet<>();
    }

    public UsuarioModel(String nome, String username, String password, TipoUsuario tipo) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.tarefas = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public Set<TarefaModel> getTarefas() {
        return tarefas;
    }

    public void setTarefas(TarefaModel tarefa) {
        this.tarefas.add(tarefa);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (tipo) {
            case USUARIO ->  List.of(
                    new SimpleGrantedAuthority("ROLE_USER")
            );
            case ADMIN -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        };
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
