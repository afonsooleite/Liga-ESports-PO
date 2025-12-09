package Backend;

import java.io.Serializable;

public class JogadorEfootball extends Jogador implements Serializable {

    private String posicao;
    private int golosMarcados;
    private int golosDefendidos;
    private int assistencias;

    public JogadorEfootball(String nomeCompleto, String nickname, String senha, String posicao, int golosMarcados, int golosDefendidos, int assistencias) {
        super(nomeCompleto, nickname, senha);
        if (posicao == null || posicao.trim().isEmpty()) {
            throw new IllegalArgumentException("A posição principal não pode ser vazia.");
        }
        if (golosMarcados < 0 || golosDefendidos < 0 || assistencias < 0) {
            throw new IllegalArgumentException("Os valores de estatísticas não podem ser negativos.");
        }
        this.posicao = posicao;
        this.golosMarcados = golosMarcados;
        this.golosDefendidos = golosDefendidos;
        this.assistencias = assistencias;
    }

    // Getters e Setters

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        if (posicao == null || posicao.trim().isEmpty()) {
            throw new IllegalArgumentException("A posição principal não pode ser vazia.");
        }
        this.posicao = posicao;
    }

    public int getGolosMarcados() {
        return golosMarcados;
    }

    public void setGolosMarcados(int golosMarcados) {
        if (golosMarcados < 0) {
            throw new IllegalArgumentException("O número de golos marcados não pode ser negativo.");
        }
        this.golosMarcados = golosMarcados;
    }

    public int getGolosDefendidos() {
        return golosDefendidos;
    }

    public void setGolosDefendidos(int golosDefendidos) {
        if (golosDefendidos < 0) {
            throw new IllegalArgumentException("O número de golos defendidos não pode ser negativo.");
        }
        this.golosDefendidos = golosDefendidos;
    }

    public int getAssistencias() {
        return assistencias;
    }

    public void setAssistencias(int assistencias) {
        if (assistencias < 0) {
            throw new IllegalArgumentException("O número de assistências não pode ser negativo.");
        }
        this.assistencias = assistencias;
    }

    // Atualizar estatísticas

      public void atualizarEstatisticasEfootball(int novosGolsMarcados, int novasAssistencias, int novosGolsDefendidos) {
        this.golosMarcados += novosGolsMarcados;
        this.assistencias += novasAssistencias;
        this.golosDefendidos += novosGolsDefendidos;
        salvarJogador();
    }

    @Override
    public String exibirEstatisticas() {
        return super.exibirEstatisticas() + String.format(
                " Posição: %s | Golos Marcados: %d | Golos Defendidos: %d | Assistências: %d",
                posicao, golosMarcados, golosDefendidos, assistencias);
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %s | Nome: %s | Nickname: %s | Posição: %s | Golos Marcados: %d | Golos Defendidos: %d | Assistências: %d",
                getId(), getNomeCompleto(), getNickname(), posicao, golosMarcados, golosDefendidos, assistencias);
    }
     @Override
    public void adicionarTorneio(Torneio torneio) {
        super.adicionarTorneio(torneio);
        
    }
}
