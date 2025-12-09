package Frontend;

import java.util.Scanner;

/**
 * Classe que gerencia a interação com o usuário via console.
 */
public class Consola {

    private final Scanner teclado;

    /**
     * Construtor que inicializa o scanner para entrada de dados.
     */
    public Consola() {
        this.teclado = new Scanner(System.in);
    }

    /**
     * Exibe uma mensagem no console.
     * 
     * @param texto Texto a ser exibido.
     */
    public void escreverTexto(String texto) {
        System.out.println(texto);
    }

    /**
     * Exibe uma mensagem de erro no console.
     * 
     * @param texto Texto do erro a ser exibido.
     */
    public void escreverErro(String texto) {
        System.err.println("[ERRO] " + texto);
    }

    /**
     * Lê uma string inserida pelo usuário.
     * 
     * @param mensagem Mensagem de solicitação.
     * @return Texto digitado pelo usuário.
     */
    public String lerString(String mensagem) {
        String entrada;
        do {
            System.out.println(mensagem);
            entrada = teclado.nextLine().trim();
            if (entrada.isEmpty()) {
                System.err.println("Entrada inválida. Por favor, tente novamente.");
            }
        } while (entrada.isEmpty());
        return entrada;
    }

    /**
     * Lê um número inteiro inserido pelo usuário, com validação.
     * 
     * @param mensagem Mensagem de solicitação.
     * @return Número inteiro digitado pelo usuário.
     */
    public int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.println(mensagem);
                return Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                escreverErro("Por favor, insira um número inteiro válido.");
            }
        }
    }

    /**
     * Lê um número decimal inserido pelo usuário, com validação.
     * 
     * @param mensagem Mensagem de solicitação.
     * @return Número decimal digitado pelo usuário.
     */
    public double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.println(mensagem);
                return Double.parseDouble(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                escreverErro("Por favor, insira um número decimal válido.");
            }
        }
    }

    /**
     * Exibe um menu e lê a opção escolhida pelo usuário.
     * 
     * @param opcoes Opções do menu.
     * @return Índice da opção escolhida.
     */
    public int lerOpcaoMenu(String[] opcoes) {
        for (int i = 0; i < opcoes.length; i++) {
            System.out.printf("%d - %s%n", i + 1, opcoes[i]);
        }
        int escolha;
        do {
            escolha = lerInteiro("Escolha uma opção do menu:");
            if (escolha < 1 || escolha > opcoes.length) {
                escreverErro("Opção inválida. Escolha novamente.");
            }
        } while (escolha < 1 || escolha > opcoes.length);

        return escolha;
    }

    /**
     * Fecha o scanner para evitar vazamento de recursos.
     */
    public void fecharScanner() {
        teclado.close();
    }
}