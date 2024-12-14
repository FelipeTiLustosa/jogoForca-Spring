package com.dev.felipe.jogodaforca.repositorios;

import com.dev.felipe.jogodaforca.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankingRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u ORDER BY u.melhorPontuacao DESC LIMIT 10")
    List<Usuario> obterTop10Ranking();
}
