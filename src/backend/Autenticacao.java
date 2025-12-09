package Backend;

import Frontend.Consola;
import java.util.ArrayList;
import java.util.List;

public class Autenticacao {

    private List<Jogador> jogadores = new ArrayList<>();
    private final List<JogadorFPS> jogadoresFps;
    private final List<JogadorMOBA> jogadoresMoba;
    private final List<JogadorEfootball> jogadoresEfootball;
    private final List<Treinador> treinadores;
    private final List<Administrador> administradores;
    private final Consola consola;

    public Autenticacao(List<JogadorFPS> jogadoresFps, List<JogadorMOBA> jogadoresMoba, List<JogadorEfootball> jogadoresEfootball,
            List<Treinador> treinadores, List<Administrador> administradores, Consola consola) {
        if (jogadoresFps == null || jogadoresMoba == null || jogadoresEfootball == null
                || treinadores == null || administradores == null || consola == null) {
            throw new IllegalArgumentException("Nenhuma das listas de usuários ou a consola pode ser nula.");
        }
        this.jogadoresFps = jogadoresFps;
        this.jogadoresMoba = jogadoresMoba;
        this.jogadoresEfootball = jogadoresEfootball;
        this.treinadores = treinadores;
        this.administradores = administradores;
        this.consola = consola;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public List<Treinador> getTreinadores() {
        return treinadores;
    }

    public List<Jogador> getJogadores() {
        if (jogadores == null) {
            jogadores = new ArrayList<>();
        }
        return jogadores;
    }

    public JogadorFPS autenticarJogadorFps(String nickname, String senha) {
        validarCredenciais(nickname, senha);
        for (JogadorFPS jogador : jogadoresFps) {
            if (jogador.getNickname().equals(nickname) && jogador.getSenha().equals(senha)) {
                return jogador;
            }
        }
        return null;
    }

    public JogadorMOBA autenticarJogadorMoba(String nickname, String senha) {
        validarCredenciais(nickname, senha);
        for (JogadorMOBA jogador : jogadoresMoba) {
            if (jogador.getNickname().equals(nickname) && jogador.getSenha().equals(senha)) {
                return jogador;
            }
        }
        return null;
    }

    public JogadorEfootball autenticarJogadorEfootball(String nickname, String senha) {
        validarCredenciais(nickname, senha);
        for (JogadorEfootball jogador : jogadoresEfootball) {
            if (jogador.getNickname().equals(nickname) && jogador.getSenha().equals(senha)) {
                return jogador;
            }
        }
        return null;
    }

    public Treinador autenticarTreinador(String email, String senha) {
        validarCredenciais(email, senha);
        for (Treinador treinador : treinadores) {
            if (treinador.getEmail().equals(email) && treinador.getSenha().equals(senha)) {
                return treinador;
            }
        }
        return null;
    }

    public Administrador autenticarAdministrador(String email, String senha) {
        validarCredenciais(email, senha);
        for (Administrador administrador : administradores) {
            if (administrador.getEmail().equals(email) && administrador.getSenha().equals(senha)) {
                return administrador;
            }
        }
        return null;
    }

    public void criarConta(String tipo, String nome, String emailNick, String senha) {
        validarCredenciais(emailNick, senha);
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }

        switch (tipo) {
            case "Jogador FPS" -> {
                JogadorFPS jogadorFPS = new JogadorFPS(nome, emailNick, senha, 0.0, 0);
                jogadoresFps.add(jogadorFPS);
                consola.escreverTexto("Jogador FPS criado com sucesso.");
            }
            case "Jogador MOBA" -> {
                JogadorMOBA jogadorMOBA = new JogadorMOBA(nome, emailNick, senha, "Personagem Padrão", 0, 0, 0);
                jogadoresMoba.add(jogadorMOBA);
                consola.escreverTexto("Jogador MOBA criado com sucesso.");
            }
            case "Jogador Efootball" -> {
                JogadorEfootball jogadorEfootball = new JogadorEfootball(nome, emailNick, senha, "Posição Padrão", 0, 0, 0);
                jogadoresEfootball.add(jogadorEfootball);
                consola.escreverTexto("Jogador Efootball criado com sucesso.");
            }
            case "Treinador" -> {

                Treinador treinador = new Treinador(nome, emailNick, senha, null);
                treinadores.add(treinador);
                consola.escreverTexto("Treinador criado com sucesso!");

            }
            case "Administrador" -> {
                Administrador administrador = new Administrador(nome, emailNick, senha);
                administradores.add(administrador);
                consola.escreverTexto("Administrador criado com sucesso.");
            }
            default ->
                consola.escreverErro("Tipo de conta inválido.");
        }
    }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    public void adicionarTreinador(Treinador treinador) {
        treinadores.add(treinador);
    }

    public void adicionarAdministrador(Administrador administrador) {
        administradores.add(administrador);
    }

    private void validarCredenciais(String identificador, String senha) {
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("O identificador (e-mail ou nickname) não pode ser vazio.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }
    }
}
