package com.dev.felipe.jogodaforca.repositorios;

import com.dev.felipe.jogodaforca.entidades.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PalavraRepository extends JpaRepository<Palavra, Long> {
    @Query("SELECT p FROM Palavra p ORDER BY RANDOM() LIMIT 1")
    Palavra sortearPalavraAleatoria();
}
