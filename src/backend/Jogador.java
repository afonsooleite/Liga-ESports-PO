package Backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jogador implements Serializable {

    private static int nextId = 1;
    private final int id;
    private String nomeCompleto;
    private String nickname;
    private String passe;
    public int partidasJogadas;
    private int vitorias;
    private int derrotas;
    private final List<Torneio> torneiosAParticipar;

    public Jogador(String nomeCompleto, String nickname, String passe) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome completo não pode ser vazio.");
        }
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("O nickname não pode ser vazio.");
        }
        if (passe == null || passe.trim().isEmpty()) {
            throw new IllegalArgumentException("A palavra-passe não pode estar vazia.");
        }
        this.id = nextId++;
        this.nomeCompleto = nomeCompleto;
        this.nickname = nickname;
        this.passe = passe;
        this.partidasJogadas = 0;
        this.vitorias = 0;
        this.derrotas = 0;
        this.torneiosAParticipar = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome completo não pode ser vazio.");
        }
        this.nomeCompleto = nomeCompleto;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("O nickname não pode ser vazio.");
        }
        this.nickname = nickname;
    }

    public String getSenha() {
        return passe;
    }

    public void setSenha(String passe) {
        if (passe == null || passe.trim().isEmpty()) {
            throw new IllegalArgumentException("A palavra-passe não pode estar vazia.");
        }
        this.passe = passe;
    }

    public int getPartidasJogadas() {
        return partidasJogadas;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public List<Torneio> getTorneiosAParticipar() {
        return new ArrayList<>(torneiosAParticipar);
    }
    public String getDadosPessoais() {
        return "Nome: " + nomeCompleto + "\n"
                + "Nickname: " + nickname + "\n"
                + "Número de partidas jogadas: " + partidasJogadas + "\n"
                + "Número de vitórias: " + vitorias + "\n"
                + "Número de derrotas: " + derrotas;
    }

    // Métodos Principais
    public boolean autenticar(String nickname, String senha) {
        if (nickname == null || senha == null) {
            throw new IllegalArgumentException("Nickname e senha não podem ser nulos.");
        }
        return this.nickname.equals(nickname) && this.passe.equals(senha);
    }

   

    public void adicionarTorneio(Torneio torneio) {
        if (torneio == null) {
            throw new IllegalArgumentException("Torneio não pode ser nulo.");
        }
        if (!torneiosAParticipar.contains(torneio)) {
            torneiosAParticipar.add(torneio);
        } else {
            System.out.println("Jogador já está inscrito neste torneio.");
        }
        salvarJogador();
    }

    public void registarPartida(boolean venceu) {
        partidasJogadas++;
        if (venceu) {
            vitorias++;
        } else {
            derrotas++;
        }
        salvarJogador();
    }

    public boolean editarDadosPessoais(String novoNome, String novoNickname) {
        boolean alterado = false;

        if (novoNome != null && !novoNome.trim().isEmpty()) {
            this.nomeCompleto = novoNome;
            alterado = true;
        }

        if (novoNickname != null && !novoNickname.trim().isEmpty()) {
            this.nickname = novoNickname;
            alterado = true;
        }
        salvarJogador();
        return alterado;
    }

    // Métodos para visualização
    public String exibirDadosPessoais() {
        return String.format("ID: %s, Nome completo: %s, Nickname: %s",
                id, nomeCompleto, nickname);
    }

    public String exibirEstatisticas() {
        return String.format("Total de partidas: %d | Vitórias: %d | Derrotas: %d",
                partidasJogadas, vitorias, derrotas);
    }

    public void exibirTorneios() {
        System.out.println("Torneios em que " + nickname + " participa:");
        if (torneiosAParticipar.isEmpty()) {
            System.out.println("Nenhum torneio registrado.");
        } else {
            for (Torneio torneio : torneiosAParticipar) {
                System.out.println("- " + torneio.getNome());
            }
        }
    }
    public void salvarJogador() {
    try {
        List<Jogador> jogadores = GestorDeFicheiros.lerJogadores(); // Carrega jogadores existentes
        if (jogadores == null) {
            jogadores = new ArrayList<>();
        }

        // Atualiza ou adiciona o jogador na lista
        jogadores.removeIf(j -> j.getId() == this.id);
        jogadores.add(this);

        GestorDeFicheiros.guardarJogadores(jogadores); // Salva no arquivo
        System.out.println("Jogador " + nickname + " salvo com sucesso!");
    } catch (Exception e) {
        System.err.println("Erro ao salvar o jogador: " + e.getMessage());
    }
}


    @Override
    public String toString() {
        return String.format("ID: %s | Nome: %s | Nickname: %s",
                id,nomeCompleto, nickname);
    }
}
