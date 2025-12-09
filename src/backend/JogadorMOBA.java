package Backend;

import java.io.Serializable;

/**
 * Classe que representa um jogador especializado em jogos do tipo MOBA.
 */
public class JogadorMOBA extends Jogador implements Serializable {

    private String personagemPrincipal;
    private int kills;
    private int deaths;
    private int assists;

    public JogadorMOBA(String nomeCompleto, String nickname, String senha, String personagemPrincipal, int kills, int deaths, int assists) {
        super(nomeCompleto, nickname, senha);

        if (personagemPrincipal == null || personagemPrincipal.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do personagem principal não pode ser vazio.");
        }
        if (kills < 0 || deaths < 0 || assists < 0) {
            throw new IllegalArgumentException("As estatísticas não podem ter valores negativos.");
        }

        this.personagemPrincipal = personagemPrincipal;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
    }

    // Getters e Setters
    public String getPersonagemPrincipal() {
        return personagemPrincipal;
    }

    public void setPersonagemPrincipal(String personagemPrincipal) {
        if (personagemPrincipal == null || personagemPrincipal.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do personagem principal não pode ser vazio.");
        }
        this.personagemPrincipal = personagemPrincipal;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        if (kills < 0) {
            throw new IllegalArgumentException("O número de kills não pode ser negativo.");
        }
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        if (deaths < 0) {
            throw new IllegalArgumentException("O número de deaths não pode ser negativo.");
        }
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        if (assists < 0) {
            throw new IllegalArgumentException("O número de assists não pode ser negativo.");
        }
        this.assists = assists;
    }

    // Cálculo do KDA (Kill/Death/Assist ratio)
    public double getKDA() {
        return (deaths > 0) ? (double) (kills + assists) / deaths : kills + assists;
    }

    // Atualização de estatísticas
    public void atualizarEstatisticasMOBA(int kills, int assists, int deaths) {
        this.kills += kills;
        this.assists += assists;
        this.deaths += deaths;
        salvarJogador();
    }

    @Override
    public void adicionarTorneio(Torneio torneio) {
        super.adicionarTorneio(torneio);

    }

    // Exibição de Estatísticas
    @Override
    public String exibirEstatisticas() {
        return super.exibirEstatisticas() + String.format(
                " Personagem: %s | Kills: %d | Deaths: %d | Assists: %d | KDA: %.2f",
                personagemPrincipal, kills, deaths, assists, getKDA());
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %s | Nome: %s | Nickname: %s | Personagem: %s | Kills: %d | Deaths: %d | Assists: %d | KDA: %.2f",
                getId(), getNomeCompleto(), getNickname(), personagemPrincipal, kills, deaths, assists, getKDA());
    }

}
