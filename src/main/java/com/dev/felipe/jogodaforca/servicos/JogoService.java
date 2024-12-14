package com.dev.felipe.jogodaforca.servicos;

import com.dev.felipe.jogodaforca.entidades.Palavra;
import com.dev.felipe.jogodaforca.entidades.Usuario;
import com.dev.felipe.jogodaforca.repositorios.PalavraRepository;
import com.dev.felipe.jogodaforca.repositorios.RankingRepository;
import com.dev.felipe.jogodaforca.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class JogoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PalavraRepository palavraRepository;

    @Autowired
    private RankingRepository rankingRepository;

    public Usuario obterOuCriarUsuario(String nickname) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByNickname(nickname);
        return usuarioExistente.orElseGet(() -> {
            Usuario novoUsuario = new Usuario(nickname);
            return usuarioRepository.save(novoUsuario);
        });
    }

    public Palavra sortearPalavra() {
        return palavraRepository.sortearPalavraAleatoria();
    }

    public Map<String, Object> processarLetra(String letra, String palavraOriginal, String palavraAtual, String letrasCorretas, int tentativas) {
        Map<String, Object> resposta = new HashMap<>();
        letra = letra.toUpperCase();
        palavraOriginal = palavraOriginal.toUpperCase();

        if (letrasCorretas.contains(letra)) {
            resposta.put("status", "repetida");
            return resposta;
        }

        if (palavraOriginal.contains(letra)) {
            StringBuilder novaPalavraAtual = new StringBuilder(palavraAtual);
            for (int i = 0; i < palavraOriginal.length(); i++) {
                if (palavraOriginal.charAt(i) == letra.charAt(0)) {
                    novaPalavraAtual.setCharAt(2*i, letra.charAt(0));
                }
            }

            resposta.put("status", "correta");
            resposta.put("palavraAtual", novaPalavraAtual.toString());
            resposta.put("letrasCorretas", letrasCorretas + letra);
        } else {
            resposta.put("status", "incorreta");
            tentativas--;
        }

        resposta.put("tentativas", tentativas);
        return resposta;
    }

    public void atualizarPontuacao(Usuario usuario, int novaPontuacao, boolean vitoria) {
        if (vitoria && novaPontuacao > usuario.getMelhorPontuacao()) {
            usuario.setMelhorPontuacao(novaPontuacao);
            usuario.setMelhorDataJogo(LocalDateTime.now());
            usuarioRepository.save(usuario);
        }
    }

    public List<Usuario> obterRanking() {
        return rankingRepository.obterTop10Ranking();
    }
}