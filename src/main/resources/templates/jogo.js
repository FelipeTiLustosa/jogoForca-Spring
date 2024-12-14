let tentativasRestantes = 6;
let palavra = '[[${palavra.palavra}]]';
let palavraAtual = palavra.split('').map(() => '_').join(' ');


function inicializarJogo() {
    const container = document.getElementById('palavraContainer');
    container.innerHTML = palavraAtual.split(' ').join(' ');
    document.getElementById('tentativasRestantes').innerText = tentativasRestantes;
    atualizarImagemForca();
}


function verificarLetra() {
    const letraInput = document.getElementById('letraInput');
    const letra = letraInput.value.toUpperCase();
    const mensagemStatus = document.getElementById('mensagemStatus');

    if (!letra || letra.length !== 1) {
        mensagemStatus.innerHTML = 'Por favor, digite uma letra.';
        return;
    }


    fetch('/verificar-letra', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `letra=${letra}&palavraAtual=${encodeURIComponent(palavraAtual)}&tentativas=${tentativasRestantes}&palavraOriginal=${palavra}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'repetida') {
                mensagemStatus.innerHTML = 'Letra já digitada!';
            } else if (data.status === 'correta') {
                palavraAtual = data.palavraAtual;
                document.getElementById('palavraContainer').innerHTML = palavraAtual.split(' ').join(' ');
                tentativasRestantes = data.tentativasRestantes;
                atualizarImagemForca();
                mensagemStatus.innerHTML = 'Letra correta!';
            } else {
                tentativasRestantes = data.tentativasRestantes;
                document.getElementById('tentativasRestantes').innerText = tentativasRestantes;
                atualizarImagemForca();
                mensagemStatus.innerHTML = 'Letra incorreta!';
            }


            letraInput.value = '';
            // Checa se o jogo terminou
            if (tentativasRestantes === 0) {
                finalizarJogo(false);
            }
        });
}

function atualizarImagemForca() {
    const imagemForca = document.getElementById('imagemForca');
    // A imagem será carregada com base no número de tentativas restantes
    imagemForca.innerHTML = `<img src="/imagens/forca-${6 - tentativasRestantes}.png" alt="Forca" class="img-fluid" style="max-height: 250px;">`;
}

// Função para finalizar o jogo
function finalizarJogo(vitoria) {
    const mensagemStatus = document.getElementById('mensagemStatus');
    if (vitoria) {
        mensagemStatus.innerHTML = 'Parabéns! Você acertou a palavra!';
    } else {
        mensagemStatus.innerHTML = `Game Over! A palavra era: ${palavra}`;
    }

    setTimeout(() => {
        window.location.href = '/';  // Redireciona para a página inicial após alguns segundos
    }, 3000);
}

// Chama a função de inicialização ao carregar a página
window.onload = inicializarJogo;
