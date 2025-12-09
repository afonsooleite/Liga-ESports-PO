package Backend;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Torneio implements Serializable {

    private static int nextId = 1;
    private final int id;
    private final String nome;
    private final String jogo;
    private final List<Equipa> equipas;
    private final List<Partida> partidas;
    private final Map<Equipa, Integer> classificacao;

    public Torneio(String nome, String jogo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do torneio não pode ser vazio.");
        }
        if (jogo == null || jogo.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo do tipo de jogo não pode ser vazio.");
        }

        this.id = nextId++;
        this.nome = nome;
        this.jogo = jogo;
        this.equipas = new ArrayList<>();
        this.partidas = new ArrayList<>();
        this.classificacao = new HashMap<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getJogo() {
        return jogo;
    }

    public List<Equipa> getEquipas() {
        return new ArrayList<>(equipas);
    }

    public List<Partida> getPartidas() {
        return new ArrayList<>(partidas);
    }

    public Map<Equipa, Integer> getClassificacao() {
        return new HashMap<>(classificacao);
    }

    // Métodos principais
    public void adicionarEquipa(Equipa equipa) {
        if (equipa == null) {
            throw new IllegalArgumentException("A equipa não pode ser nula.");
        }
        if (!equipas.contains(equipa)) {
            equipas.add(equipa);
            classificacao.put(equipa, 0); // Inicializa com 0 pontos
            System.out.println("Equipa " + equipa.getNome() + " adicionada ao torneio " + nome + ".");
        } else {
            System.err.println("A equipa já está inscrita no torneio " + nome + ".");
        }
        salvarTorneio(); 
    }

    public void adicionarPartida(Partida partida) {
        if (partida == null) {
            throw new IllegalArgumentException("A partida não pode ser nula.");
        }
        if (!equipas.contains(partida.getEquipaA()) || !equipas.contains(partida.getEquipaB())) {
            throw new IllegalArgumentException("Ambas as equipas devem estar inscritas no torneio.");
        }
        partidas.add(partida);
        atualizarClassificacao(partida);
        salvarTorneio(); 
        System.out.println("Partida entre " + partida.getEquipaA().getNome() + " e " + partida.getEquipaB().getNome() + " adicionada ao torneio " + nome + ".");
    }

    private void atualizarClassificacao(Partida partida) {
        Equipa equipaVencedora = null;
        if (partida.getPontosEquipaA() > partida.getPontosEquipaB()) {
            equipaVencedora = partida.getEquipaA();
        } else if (partida.getPontosEquipaB() > partida.getPontosEquipaA()) {
            equipaVencedora = partida.getEquipaB();
        }

        if (equipaVencedora != null) {
            classificacao.put(equipaVencedora, classificacao.getOrDefault(equipaVencedora, 0) + 3);
        } else {
            classificacao.put(partida.getEquipaA(), classificacao.getOrDefault(partida.getEquipaA(), 0) + 1);
            classificacao.put(partida.getEquipaB(), classificacao.getOrDefault(partida.getEquipaB(), 0) + 1);
        }
        salvarTorneio(); 
    }

    public void exibirClassificacao() {
        System.out.println("=== Classificação Atual ===");
        List<Map.Entry<Equipa, Integer>> listaClassificacao = new ArrayList<>(classificacao.entrySet());
        for (int i = 0; i < listaClassificacao.size() - 1; i++) {
            for (int j = i + 1; j < listaClassificacao.size(); j++) {
                if (listaClassificacao.get(j).getValue() > listaClassificacao.get(i).getValue()) {
                    Map.Entry<Equipa, Integer> temp = listaClassificacao.get(i);
                    listaClassificacao.set(i, listaClassificacao.get(j));
                    listaClassificacao.set(j, temp);
                }
            }
        }
        for (Map.Entry<Equipa, Integer> entry : listaClassificacao) {
            System.out.printf("Equipa: %s | Pontos: %d%n", entry.getKey().getNome(), entry.getValue());
        }
    }

    public void exibirPartidas() {
        System.out.println("\nPartidas realizadas no Torneio " + nome + ":");
        if (partidas.isEmpty()) {
            System.out.println("Nenhuma partida realizada ainda.");
        } else {
            for (Partida partida : partidas) {
                System.out.println("Partida " + partida.getId() + ": " + partida.obterResultado());
            }
        }
    }

    public String getEstatisticas() {
        StringBuilder estatisticas = new StringBuilder();
        estatisticas.append("=== Estatísticas do Torneio: ").append(nome).append(" ===\n")
                .append("Tipo de Jogo: ").append(jogo).append("\n")
                .append("\n--- Classificação ---\n");

        // Ordenação manual pela pontuação
        List<Map.Entry<Equipa, Integer>> listaClassificacao = new ArrayList<>(classificacao.entrySet());
        List<Map.Entry<Equipa, Integer>> listaOrdenada = new ArrayList<>();

        while (!listaClassificacao.isEmpty()) {
            Map.Entry<Equipa, Integer> maior = listaClassificacao.get(0);
            for (Map.Entry<Equipa, Integer> entry : listaClassificacao) {
                if (entry.getValue() > maior.getValue()) {
                    maior = entry;
                }
            }
            listaOrdenada.add(maior);
            listaClassificacao.remove(maior);
        }

        // Adiciona ao relatório
        for (Map.Entry<Equipa, Integer> entry : listaOrdenada) {
            estatisticas.append("Equipa: ").append(entry.getKey().getNome())
                    .append(" | Pontos: ").append(entry.getValue()).append("\n");
        }

        estatisticas.append("\n--- Partidas ---\n");
        if (partidas.isEmpty()) {
            estatisticas.append("Nenhuma partida realizada ainda.\n");
        } else {
            for (Partida partida : partidas) {
                estatisticas.append("Partida ").append(partida.getId()).append(": ")
                        .append(partida.obterResultado()).append("\n");
            }
        }

        return estatisticas.toString();
    }
    private void salvarTorneio() {
    try {
        List<Torneio> torneios = GestorDeFicheiros.lerTorneios(); // Carrega torneios existentes
        if (torneios == null) {
            torneios = new ArrayList<>();
        }

        // Atualiza ou adiciona o torneio na lista
        torneios.removeIf(t -> t.getId() == this.id);
        torneios.add(this);

        GestorDeFicheiros.guardarTorneios(torneios); // Salva no arquivo
        System.out.println("Torneio " + nome + " salvo com sucesso!");
    } catch (Exception e) {
        System.err.println("Erro ao salvar o torneio: " + e.getMessage());
    }
}


    @Override
    public String toString() {
        return String.format(
                "Torneio %s, Tipo de Jogo: %s, Número de Equipas: %d, Número de Partidas: %d }",
                
                nome,
                jogo,
                equipas.size(),
                partidas.size()
        );
    }
}
