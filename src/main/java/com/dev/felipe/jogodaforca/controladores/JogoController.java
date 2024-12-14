package com.dev.felipe.jogodaforca.controladores;

import com.dev.felipe.jogodaforca.entidades.Palavra;
import com.dev.felipe.jogodaforca.entidades.Usuario;
import com.dev.felipe.jogodaforca.servicos.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class JogoController {
    @Autowired
    private JogoService jogoService;

    @GetMapping("/")
    public String paginaInicial() {
        return "inicio";
    }

    @PostMapping("/iniciar-jogo")
    public String iniciarJogo(@RequestParam String apelido, Model modelo) {
        Usuario usuario = jogoService.obterOuCriarUsuario(apelido);
        Palavra palavraSorteada = jogoService.sortearPalavra();
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("palavra", palavraSorteada);
        modelo.addAttribute("ranking", jogoService.obterRanking());
        return "jogo";
    }


    @PostMapping("/verificar-letra")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verificarLetra(
            @RequestParam String letra,
            @RequestParam String palavraAtual,
            @RequestParam String letrasCorretas,
            @RequestParam int tentativas,
            @RequestParam String palavraOriginal) {

        Map<String, Object> resposta = jogoService.processarLetra(letra, palavraOriginal, palavraAtual, letrasCorretas, tentativas);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/finalizar-jogo")
    @ResponseBody
    public ResponseEntity<String> finalizarJogo(
            @RequestParam String nickname,
            @RequestParam int pontuacao,
            @RequestParam boolean vitoria) {

        Usuario usuario = jogoService.obterOuCriarUsuario(nickname);
        jogoService.atualizarPontuacao(usuario, pontuacao, vitoria);
        return ResponseEntity.ok("Jogo finalizado");
    }
}