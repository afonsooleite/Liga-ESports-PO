package Backend;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Partida implements Serializable {

    private Torneio torneio;
    private static int idCounter = 1;
    private final int id;
    private final Equipa equipaA;
    private final Equipa equipaB;
    private int pontosEquipaA;
    private int pontosEquipaB;
    private final LocalDate data;

    public Partida(Equipa equipaA, Equipa equipaB, int pontosEquipaA, int pontosEquipaB, LocalDate data) {
        if (equipaA == null || equipaB == null) {
            throw new IllegalArgumentException("As equipas A e B devem ser válidas.");
        }
        if (equipaA.equals(equipaB)) {
            throw new IllegalArgumentException("Uma partida deve ser entre equipas diferentes.");
        }
        if (data == null) {
            throw new IllegalArgumentException("A data da partida não pode ser vazia.");
        }

        this.id = idCounter++;
        this.equipaA = equipaA;
        this.equipaB = equipaB;
        this.pontosEquipaA = Math.max(pontosEquipaA, 0);
        this.pontosEquipaB = Math.max(pontosEquipaB, 0);
        this.data = data;
        this.torneio = torneio;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public Torneio getTorneio() {
        return this.torneio;
    }

    public Equipa getEquipaA() {
        return equipaA;
    }

    public Equipa getEquipaB() {
        return equipaB;
    }

    public int getPontosEquipaA() {
        return pontosEquipaA;
    }

    public void setPontosEquipaA(int pontosEquipaA) {
        if (pontosEquipaA < 0) {
            throw new IllegalArgumentException("Os pontos da equipa A não podem ser negativos.");
        }
        this.pontosEquipaA = pontosEquipaA;
        salvarPartida();
    }

    public int getPontosEquipaB() {
        return pontosEquipaB;
    }

    public void setPontosEquipaB(int pontosEquipaB) {
        if (pontosEquipaB < 0) {
            throw new IllegalArgumentException("Os pontos da equipa B não podem ser negativos.");
        }
        this.pontosEquipaB = pontosEquipaB;
        salvarPartida();
    }

    public LocalDate getData() {
        return data;
    }

    // Métodos Principais
    public String getVencedor() {
        if (pontosEquipaA > pontosEquipaB) {
            return equipaA.getNome();
        } else if (pontosEquipaB > pontosEquipaA) {
            return equipaB.getNome();
        } else {
            return "Empate";
        }
    }

    public boolean isEmpate() {
        return pontosEquipaA == pontosEquipaB;
    }

    public String obterResultado() {
        if (pontosEquipaA > pontosEquipaB) {
            return "Vitória da equipa " + equipaA.getNome() + " (" + pontosEquipaA + " - " + pontosEquipaB + ")";
        } else if (pontosEquipaB > pontosEquipaA) {
            return "Vitória da equipa " + equipaB.getNome() + " (" + pontosEquipaB + " - " + pontosEquipaA + ")";
        } else {
            return "Empate (" + pontosEquipaA + " - " + pontosEquipaB + ")";
        }
    }

    public void exibirDetalhes() {
        System.out.println("Partida " + id + ":");
        System.out.println("Data: " + data);
        System.out.println("Equipa A: " + equipaA.getNome() + " - Pontos: " + pontosEquipaA);
        System.out.println("Equipa B: " + equipaB.getNome() + " - Pontos: " + pontosEquipaB);
        System.out.println("Resultado: " + obterResultado());
    }

    @Override
    public String toString() {
        return String.format(
                "Equipa A: %s | Equipa B: %s | Resultado: %d - %d | Data: %s | Vencedor: %s",
                equipaA.getNome(), equipaB.getNome(), pontosEquipaA, pontosEquipaB, data, getVencedor()
        );
    }

    private void salvarPartida() {
    if (this.torneio == null) {
        throw new IllegalStateException("A partida não está associada a nenhum torneio.");
    }
    try {
        List<Torneio> torneios = GestorDeFicheiros.lerTorneios();
        if (torneios == null) {
            torneios = new ArrayList<>();
        }

        // Atualizar ou adicionar o torneio associado
        torneios.removeIf(t -> t.getId() == this.torneio.getId());
        torneios.add(this.torneio);

        GestorDeFicheiros.guardarTorneios(torneios); // Salva no arquivo
        System.out.println("Partida salva no torneio " + this.torneio.getNome() + " com sucesso!");
    } catch (Exception e) {
        System.err.println("Erro ao salvar a partida: " + e.getMessage());
    }
}

}



