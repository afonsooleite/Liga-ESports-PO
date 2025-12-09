package Backend;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorDeFicheiros {

    // Método genérico para guardar objetos em ficheiro
    public static void guardarEmFicheiro(Object objeto, String caminhoFicheiro) {
        try (FileOutputStream fos = new FileOutputStream(caminhoFicheiro);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(objeto); // Sobrescreve os dados no ficheiro
            
        } catch (IOException e) {
            System.err.println("Erro ao guardar os dados no ficheiro '" + caminhoFicheiro + "': " + e.getMessage());
        }
    }

    // Método genérico para carregar listas de objetos de um ficheiro
    @SuppressWarnings("unchecked")
    public static <T> List<T> lerListaDeFicheiro(String caminhoFicheiro, Class<T> tipoClasse) {
        File ficheiro = new File(caminhoFicheiro);

        if (!ficheiro.exists()) {
            System.out.println("Ficheiro não encontrado: " + caminhoFicheiro);
            return new ArrayList<>();
        }

        try (FileInputStream fis = new FileInputStream(ficheiro);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object dados = ois.readObject();
            if (dados instanceof List<?> lista) {
                                List<T> listaFiltrada = new ArrayList<>();
                for (Object item : lista) {
                    if (tipoClasse.isInstance(item)) {
                        listaFiltrada.add(tipoClasse.cast(item));
                    } else {
                        System.err.println("Aviso: Objeto incompatível encontrado e ignorado: " + item);
                    }
                }
                return listaFiltrada;
            } else {
                System.err.println("Erro: O conteúdo do ficheiro não é uma lista válida.");
                return new ArrayList<>();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados do ficheiro: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Métodos específicos para cada tipo de dado
    public static void guardarJogadores(List<Jogador> jogadores) {
        guardarEmFicheiro(jogadores, "jogadores.dat");
    }

    public static List<Jogador> lerJogadores() {
        return lerListaDeFicheiro("jogadores.dat", Jogador.class);
    }

    public static void guardarTreinadores(List<Treinador> treinadores) {
        guardarEmFicheiro(treinadores, "treinadores.dat");
    }

    public static List<Treinador> lerTreinadores() {
        return lerListaDeFicheiro("treinadores.dat", Treinador.class);
    }

    public static void guardarAdministradores(List<Administrador> administradores) {
        guardarEmFicheiro(administradores, "administradores.dat");
    }

    public static List<Administrador> lerAdministradores() {
        return lerListaDeFicheiro("administradores.dat", Administrador.class);
    }

    public static void guardarTorneios(List<Torneio> torneios) {
        guardarEmFicheiro(torneios, "torneios.dat");
    }

    public static List<Torneio> lerTorneios() {
        return lerListaDeFicheiro("torneios.dat", Torneio.class);
    }
    

    public static void guardarPartidas(List<Partida> jogos) {
        guardarEmFicheiro(jogos, "partidas.dat");
    }

    public static List<Partida> lerPartidas() {
        return lerListaDeFicheiro("partidas.dat", Partida.class);
    }
    
    public static void guardarEquipas(List<Equipa> equipas) {
        guardarEmFicheiro(equipas, "equipas.dat");
    }

    public static List<Equipa> lerEquipas() {
        return lerListaDeFicheiro("equipas.dat", Equipa.class);
    }

    // Método para visualizar os dados de qualquer ficheiro
    public static void visualizarDadosDeFicheiro(String caminhoFicheiro, Class<?> tipoClasse) {
        List<?> dados = lerListaDeFicheiro(caminhoFicheiro, tipoClasse);

        if (dados.isEmpty()) {
            System.out.println("Nenhum dado encontrado no ficheiro: " + caminhoFicheiro);
            return;
        }

        System.out.println("Dados encontrados no ficheiro " + caminhoFicheiro + ":");
        for (Object item : dados) {
            System.out.println(item);
        }
    }
}
