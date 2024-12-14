package com.dev.felipe.jogodaforca.repositorios;

import com.dev.felipe.jogodaforca.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNickname(String nickname);
}
