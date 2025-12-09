package Backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipa implements Serializable {

    private static int nextId = 1;
    private final int idEquipa;
    private String nome;
    private List<Jogador> listaJogadores;
    private List<Torneio> listaTorneios;
    private int vitorias;
    private int derrotas;
    private Treinador treinador;

    public Equipa(String nome, Treinador treinador) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo do nome da equipa não pode estar vazio.");
        }
        this.idEquipa = nextId++;
        this.nome = nome;
        this.treinador = treinador;
        this.listaJogadores = new ArrayList<>();
        this.listaTorneios = new ArrayList<>();
        this.vitorias = 0;
        this.derrotas = 0;
    }

    // Getters e Setters
    public List<Jogador> getJogadores() {
        return new ArrayList<>(listaJogadores);
    }

    public int getIdEquipa() {
        return idEquipa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O campo do nome da equipa não pode ser vazio.");
        }
        this.nome = nome;
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        if (vitorias < 0) {
            throw new IllegalArgumentException("O campo do número de vitórias não pode ser negativo.");
        }
        this.vitorias = vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        if (derrotas < 0) {
            throw new IllegalArgumentException("O campo do número de derrotas não pode ser negativo.");
        }
        this.derrotas = derrotas;
    }

    public List<Jogador> getListaJogadores() {
        return new ArrayList<>(listaJogadores);
    }

    public List<Torneio> getListaTorneios() {
        return new ArrayList<>(listaTorneios);
    }

    // Métodos Principais
    public boolean adicionarJogador(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("O campo do Jogador não pode ser vazio.");
        }
        if (listaJogadores.contains(jogador)) {
            System.out.println("O Jogador já está nesta equipa.");
            return false;
        }
        listaJogadores.add(jogador);

        // Sincronização dos torneios da equipa com o jogador
        for (Torneio torneio : listaTorneios) {
            jogador.adicionarTorneio(torneio);
        }
        guardarEquipa();
        return true;
    }

    public boolean removerJogador(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("O campo do Jogador não pode ser nulo.");
        }
        if (!listaJogadores.contains(jogador)) {
            System.out.println("O Jogador não foi encontrado na equipa.");
            return false;
        }
        boolean removido = listaJogadores.remove(jogador);
        guardarEquipa(); // Salvar após a remoção
        return removido;
    }

    public boolean inscreverEmTorneio(Torneio torneio) {
        if (torneio == null) {
            throw new IllegalArgumentException("O campo do Torneio não pode ser nulo.");
        }
        if (listaTorneios.contains(torneio)) {
            System.out.println("A equipa já está inscrita neste torneio.");
            return false;
        }
        listaTorneios.add(torneio);

        // Sincronizar o torneio com todos os jogadores da equipa
        for (Jogador jogador : listaJogadores) {
            jogador.adicionarTorneio(torneio);
        }
        guardarEquipa();
        return true;
    }

    public void exibirTorneios() {
        if (listaTorneios.isEmpty()) {
            System.out.println("A equipa " + nome + " não está inscrita em nenhum torneio.");
        } else {
            System.out.println("Torneios inscritos da equipa " + nome + ":");
            for (Torneio torneio : listaTorneios) {
                System.out.println("- " + torneio.getNome());
            }
        }
    }

    public void registarVitoria() {
        this.vitorias++;
        System.out.println("Vitória registrada para a equipa " + nome + ". Total de vitórias: " + vitorias);
    }

    public void registarDerrota() {
        this.derrotas++;
        System.out.println("Derrota registrada para a equipa " + nome + ". Total de derrotas: " + derrotas);
    }

    public void exibirEstatisticas() {
        System.out.println("Estatísticas da equipa " + nome + ":");
        System.out.printf("Vitórias: %d | Derrotas: %d%n", vitorias, derrotas);
        exibirJogadores();
    }

    public void exibirJogadores() {
        if (listaJogadores.isEmpty()) {
            System.out.println("Nenhum jogador na equipa " + nome + ".");
        } else {
            System.out.println("Jogadores da equipa " + nome + ":");
            for (Jogador jogador : listaJogadores) {
                System.out.println("- " + jogador.getNickname());
            }
        }
    }

    private void guardarEquipa() {
        try {
            List<Equipa> todasAsEquipas = GestorDeFicheiros.lerEquipas(); // Carrega as equipes existentes
            if (todasAsEquipas == null) {
                todasAsEquipas = new ArrayList<>();
            }

            // Substituir ou adicionar a equipa atual na lista
            todasAsEquipas.removeIf(e -> e.getIdEquipa() == this.idEquipa);
            todasAsEquipas.add(this);

            GestorDeFicheiros.guardarEquipas(todasAsEquipas); // Salva no arquivo
            System.out.println("Equipa " + nome + " guardada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao guardar a equipa: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Equipa" + nome + ", listaJogadores=" + listaJogadores + ", torneios =" + listaTorneios + ", treinador =" + treinador + ", vitorias =" + vitorias + ", derrotas =" + derrotas;
    }

}
