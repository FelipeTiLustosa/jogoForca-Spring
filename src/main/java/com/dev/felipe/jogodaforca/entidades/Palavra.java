package com.dev.felipe.jogodaforca.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "palavras")
public class Palavra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String palavra;

    @Column(nullable = false)
    private String dica;

    public Palavra() {}

    public Palavra(String palavra, String dica) {
        this.palavra = palavra;
        this.dica = dica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }
}

