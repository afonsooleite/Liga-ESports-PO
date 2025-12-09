package Backend;

import java.io.Serializable;

public class JogadorFPS extends Jogador implements Serializable {

    private int totalTiros;
    private int tirosAcertados;
    private double precisao; 
    private int headshots; 

    public JogadorFPS(String nomeCompleto, String nickname, String senha, double precisao, int headshots) {
        super(nomeCompleto, nickname, senha);
        if (precisao < 0 || precisao > 100) {
            throw new IllegalArgumentException("A precisão deve estar entre 0 e 100.");
        }
        if (headshots < 0) {
            throw new IllegalArgumentException("O número de headshots não pode ser negativo.");
        }
        this.totalTiros = 0;
        this.tirosAcertados = 0;
        this.precisao = precisao;
        this.headshots = headshots;
    }

    // Getters e Setters
    public int getTotalTiros() {
        return totalTiros;
    }

    public void setTotalTiros(int totalTiros) {
        if (totalTiros < 0) {
            throw new IllegalArgumentException("O número total de tiros não pode ser negativo.");
        }
        this.totalTiros = totalTiros;
        atualizarPrecisao();
    }

    public int getTirosAcertados() {
        return tirosAcertados;
    }

    public void setTirosAcertados(int tirosAcertados) {
        if (tirosAcertados < 0) {
            throw new IllegalArgumentException("O número total de tiros acertados não pode ser negativo.");
        }
        if (tirosAcertados > totalTiros) {
            throw new IllegalArgumentException("O número de tiros acertados não pode exceder o total de tiros.");
        }
        this.tirosAcertados = tirosAcertados;
        atualizarPrecisao();
    }

    public double getPrecisao() {
        return precisao;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        if (headshots < 0) {
            throw new IllegalArgumentException("O número de headshots não pode ser negativo.");
        }
        this.headshots = headshots;
    }

    // Métodos Principais
    @Override
    public void adicionarTorneio(Torneio torneio) {
        super.adicionarTorneio(torneio);
        
    }
    private void atualizarPrecisao() {
        this.precisao = (totalTiros > 0) ? ((double) tirosAcertados / totalTiros) * 100 : 0.0;
    }

    
    public void atualizarEstatisticasFPS(double tirosAcertados, double tirosTotais, int headshots) {
        if (tirosTotais > 0) {
            this.precisao = (this.precisao * partidasJogadas + (tirosAcertados / tirosTotais) * 100) / (partidasJogadas + 1);
        }
        this.headshots += headshots;
        atualizarPrecisao();
        salvarJogador();
    }

    // Exibição de Estatísticas
    @Override
    public String exibirEstatisticas() {
        return super.exibirEstatisticas()
                + String.format(" Tiros: %d | Acertos: %d | Precisão: %.2f%% | Headshots: %d",
                        totalTiros, tirosAcertados, precisao, headshots);
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Nome: %s | Nickname: %s | Tiros: %d | Acertos: %d | Precisão: %.2f%% | Headshots: %d",
                getId(), getNomeCompleto(), getNickname(), totalTiros, tirosAcertados, precisao, headshots);
    }
}
