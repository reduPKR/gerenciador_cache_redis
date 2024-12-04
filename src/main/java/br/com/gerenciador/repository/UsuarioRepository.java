package br.com.gerenciador.repository;

import br.com.gerenciador.model.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {
    //Derived Query Methods
    UserDetails findByUsername(String username);

    List<UsuarioModel> findByNomeStartingWith(String nome);

    //Jpql
    @Query("SELECT u FROM UsuarioModel u ORDER BY LOWER(u.nome)")
    Page<UsuarioModel> findByNomeIgnoreCase(Pageable pageable);
}
