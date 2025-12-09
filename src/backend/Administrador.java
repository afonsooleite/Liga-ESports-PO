package Backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Administrador implements Serializable {

    private static int nextId = 1;
    private final int id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private final List<Torneio> torneios;

    public Administrador(String nomeCompleto, String email, String senha) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo do nome completo não pode ser vazio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo do e-mail não pode ser vazio.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo da palavra-passe não pode ser vazio.");
        }
        this.id = nextId++;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.torneios = new ArrayList<>();
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
            throw new IllegalArgumentException("O campo do nome completo não pode ser vazio.");
        }
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo do e-mail não pode ser vazio.");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo da palavra-passe não pode ser vazio.");
        }
        this.senha = senha;
    }

    public List<Torneio> getTorneios() {
        return new ArrayList<>(torneios);
    }

// Método principal
    public boolean autenticar(String email, String senha) {
        return this.email.equalsIgnoreCase(email) && this.senha.equals(senha);
    }

    public Torneio criarTorneio(String nome, String jogo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo do nome do torneio não pode ser vazio.");
        }
        if (jogo == null || jogo.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo do nome do jogo não pode ser vazio.");
        }
        if (jogo.equalsIgnoreCase("FPS") || jogo.equalsIgnoreCase("MOBA") || jogo.equalsIgnoreCase("eFootball")) {
            Torneio novoTorneio = new Torneio(nome, jogo);
            torneios.add(novoTorneio);
            return novoTorneio;
        } else {
            throw new IllegalArgumentException("Tipo de jogo inválido. Apenas FPS, MOBA ou eFootball são permitidos.");
        }
    }

    public boolean removerTorneio(Torneio torneio) {
        if (torneio == null) {
            throw new IllegalArgumentException("O torneio fornecido é inválido (null).");
        }
        if (!torneios.contains(torneio)) {
            throw new IllegalArgumentException("O torneio " + torneio.getNome() + " não foi encontrado na lista.");
        }
        torneios.remove(torneio);
        System.out.println("Torneio " + torneio.getNome() + " removido com sucesso.");
        return true;
    }

    public void adicionarJogador(List<Jogador> jogadores, Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("O campo do jogador não pode ser vazio.");
        }
        if (jogadores.contains(jogador)) {
            throw new IllegalArgumentException(" O Jogador " + jogador.getNickname() + " já está registado.");
        }
        jogadores.add(jogador);
        System.out.println("O Jogador " + jogador.getNickname() + " foi adicionado com sucesso.");
    }

    public void removerJogador(List<Jogador> jogadores, Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("O campo do jogador não pode ser vazio.");
        }
        if (!jogadores.contains(jogador)) {
            throw new IllegalArgumentException("O Jogador " + jogador.getNickname() + " não foi encontrado.");
        }
        jogadores.remove(jogador);
        System.out.println("O Jogador " + jogador.getNickname() + " foi removido com sucesso.");
    }

    public void adicionarTreinador(List<Treinador> treinadores, Treinador treinador) {
        if (treinador == null) {
            throw new IllegalArgumentException("O campo do treinador não pode ser vazio.");
        }
        if (treinadores.contains(treinador)) {
            throw new IllegalArgumentException("O Treinador " + treinador.getNomeCompleto() + " já está registado.");
        }
        treinadores.add(treinador);
        System.out.println("O Treinador " + treinador.getNomeCompleto() + " foi adicionado com sucesso.");
    }

    public void removerTreinador(List<Treinador> treinadores, Treinador treinador) {
        if (treinador == null) {
            throw new IllegalArgumentException("O campo do treinador não pode ser vazio.");
        }
        if (!treinadores.contains(treinador)) {
            throw new IllegalArgumentException("O Treinador " + treinador.getNomeCompleto() + " não foi encontrado.");
        }
        treinadores.remove(treinador);
        System.out.println("O Treinador " + treinador.getNomeCompleto() + " foi removido com sucesso.");
    }

    public void agendarPartida(Torneio torneio, Equipa equipaA, Equipa equipaB, int pontosEquipaA, int pontosEquipaB) {
        if (torneio == null) {
            throw new IllegalArgumentException("O torneio não pode ser nulo.");
        }
        if (equipaA == null || equipaB == null) {
            throw new IllegalArgumentException("As equipas não podem ser nulas.");
        }
        if (equipaA.equals(equipaB)) {
            throw new IllegalArgumentException("As equipas não podem ser iguais.");
        }
        Partida partida = new Partida(equipaA, equipaB, pontosEquipaA, pontosEquipaB, java.time.LocalDate.now());
        torneio.adicionarPartida(partida);
        System.out.println("Partida entre " + equipaA.getNome() + " e " + equipaB.getNome() + " agendada no torneio " + torneio.getNome() + ".");
    }

    public void registarResultado(Torneio torneio, Partida partida, int pontosA, int pontosB) {
        if (torneio == null || partida == null) {
            throw new IllegalArgumentException("Torneio ou partida inválidos.");
        }

        // Configura os pontos na partida
        partida.setPontosEquipaA(pontosA);
        partida.setPontosEquipaB(pontosB);

        // Obtenha as equipas da partida
        Equipa equipaA = partida.getEquipaA();
        Equipa equipaB = partida.getEquipaB();

        if (equipaA == null || equipaB == null) {
            throw new IllegalArgumentException("Uma ou ambas as equipas não estão associadas à partida.");
        }

        // Atualizar estatísticas de ambas as equipas
        atualizarEstatisticasAleatorias(equipaA);
        atualizarEstatisticasAleatorias(equipaB);

        System.out.println("Resultado registrado com sucesso e estatísticas atualizadas.");
    }

    public void atualizarEstatisticasAleatorias(Equipa equipa) {
        if (equipa == null) {
            throw new IllegalArgumentException("Equipa inválida.");
        }

        Random random = new Random();
        for (Jogador jogador : equipa.getListaJogadores()) {
            switch (jogador) {
                case JogadorFPS jogadorFPS -> {
                    int tiros = random.nextInt(100) + 1;
                    int acertos = random.nextInt(tiros + 1);
                    int headshots = random.nextInt(acertos + 1);
                    jogadorFPS.atualizarEstatisticasFPS(acertos, tiros, headshots);
                }
                case JogadorMOBA jogadorMOBA -> {
                    int kills = random.nextInt(15);
                    int deaths = random.nextInt(10);
                    int assists = random.nextInt(8);
                    jogadorMOBA.atualizarEstatisticasMOBA(kills, assists, deaths);
                }
                case JogadorEfootball jogadorEfootball -> {
                    var golosMarcados = random.nextInt(5);
                    int assistencias = random.nextInt(3);
                    int golosDefendidos = random.nextInt(7); //
                    jogadorEfootball.atualizarEstatisticasEfootball(golosMarcados, assistencias, golosDefendidos);
                }
                default -> {
                }
            }
        }
    }

    public void acompanharEstatisticasTorneio(Torneio torneio) {
        if (torneio == null) {
            throw new IllegalArgumentException("O campo do Torneio não pode ser nulo.");
        }

        System.out.println("=== Estatísticas do Torneio: " + torneio.getNome() + " ===");
        System.out.println("Tipo de Jogo: " + torneio.getJogo());
        System.out.println("Número de Equipas Inscritas: " + torneio.getEquipas().size());

        System.out.println("\nEquipas Participantes:");
        for (Equipa equipa : torneio.getEquipas()) {
            System.out.println(" - " + equipa.getNome());
        }

        System.out.println("\nPartidas Realizadas:");
        for (Partida partida : torneio.getPartidas()) {
            System.out.println(" - " + partida.getEquipaA().getNome() + " vs " + partida.getEquipaB().getNome()
                    + " | Resultado: " + partida.getPontosEquipaA() + " - " + partida.getPontosEquipaB());
        }

        // Exibe a classificação diretamente
        torneio.exibirClassificacao();
    }

    @Override
    public String toString() {
        return String.format("Administrador %s | Email: %s ",
                nomeCompleto, email);
    }
}
