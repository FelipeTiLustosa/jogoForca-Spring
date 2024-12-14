package com.dev.felipe.jogodaforca.entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Integer melhorPontuacao = 0;

    @Column(nullable = false)
    private LocalDateTime melhorDataJogo = LocalDateTime.now();

    public Usuario() {
    }

    public Usuario(String nickname) {
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getMelhorPontuacao() {
        return melhorPontuacao;
    }

    public void setMelhorPontuacao(Integer melhorPontuacao) {
        this.melhorPontuacao = melhorPontuacao;
    }

    public LocalDateTime getMelhorDataJogo() {
        return melhorDataJogo;
    }

    public void setMelhorDataJogo(LocalDateTime melhorDataJogo) {
        this.melhorDataJogo = melhorDataJogo;
    }
}
