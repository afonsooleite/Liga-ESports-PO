package Backend;

import java.io.Serializable;
import java.util.List;

public class Treinador implements Serializable {

    private static int nextId = 1;
    private final int id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private Equipa equipa;
    private TipoTreinador tipo;

    public Treinador(String nomeCompleto, String email, String senha, TipoTreinador tipo) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome completo não pode ser vazio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email não pode ser vazio.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }
        

        this.id = nextId++;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.equipa = null;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email não pode ser vazio.");
        }
        this.email = email;
    }

    public TipoTreinador getTipo() {
        return tipo;
    }

    public void setTipo(TipoTreinador tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo de treinador não pode ser nulo. Use FPS, MOBA ou EFOOTBALL.");
        }
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }
        this.senha = senha;
    }

    public Equipa getEquipa() {
        return equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    // Métodos principais
    public void adicionarJogadorEquipa(Jogador jogador) {
        if (equipa == null) {
            throw new IllegalStateException("O treinador ainda não possui uma equipa.");
        }
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador inválido.");
        }
        if (equipa.getJogadores().contains(jogador)) {
            throw new IllegalArgumentException("Este jogador já pertence à equipa.");
        }

        equipa.adicionarJogador(jogador);

        // Vincular torneios da equipa ao jogador
        for (Torneio torneio : equipa.getListaTorneios()) {
            jogador.adicionarTorneio(torneio);
        }

        // Persistir a alteração
        GestorDeFicheiros.guardarEquipas(List.of(equipa));
    }

    public boolean removerJogadorEquipa(Jogador jogador) {
        if (equipa == null) {
            System.err.println("Você ainda não possui uma equipa associada.");
            return false;
        }
        if (jogador == null) {
            throw new IllegalArgumentException("O jogador não pode ser nulo.");
        }
        if (!equipa.getJogadores().contains(jogador)) {
            System.err.println("O jogador " + jogador.getNickname() + " não pertence à equipa " + equipa.getNome() + ".");
            return false;
        }
        boolean removido = equipa.removerJogador(jogador);
        if (removido) {
            System.out.println("O jogador " + jogador.getNickname() + " foi removido da equipa " + equipa.getNome() + ".");
        }
        return removido;
    }

    public void editarNomeEquipa(String novoNome) {
        if (this.equipa != null && novoNome != null && !novoNome.trim().isEmpty()) {
            this.equipa.setNome(novoNome); // Altera o nome da equipa
        } else {
            throw new IllegalArgumentException("O novo nome da equipa não pode ser vazio.");
        }
    }

    public void inscreverEquipa(Torneio torneio) {
        if (this.equipa == null) {
            throw new IllegalArgumentException("O treinador não possui uma equipa para inscrever.");
        }
        if (torneio.getEquipas().contains(this.equipa)) {
            throw new IllegalArgumentException("A equipa já está inscrita neste torneio.");
        }
        torneio.adicionarEquipa(this.equipa); // Apenas realiza a inscrição
    }

    public void acompanharResultadosTorneio(Torneio torneio) {
        if (equipa == null) {
            System.err.println("A equipa não está associada a este treinador.");
            return;
        }
        if (torneio == null) {
            throw new IllegalArgumentException("O torneio não pode ser nulo.");
        }

        System.out.println("\nResultados da equipa " + equipa.getNome() + " no torneio " + torneio.getNome() + ":");
        boolean encontrouPartida = false;
        for (Partida partida : torneio.getPartidas()) {
            if (partida.getEquipaA().equals(equipa) || partida.getEquipaB().equals(equipa)) {
                encontrouPartida = true;
                System.out.println("Partida " + partida.getId() + ": " + partida.obterResultado());
            }
        }

        if (!encontrouPartida) {
            System.out.println("Nenhuma partida foi realizada envolvendo a equipa " + equipa.getNome() + " até agora.");
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Nome: %s | Email: %s", id, nomeCompleto, email);
    }
}
