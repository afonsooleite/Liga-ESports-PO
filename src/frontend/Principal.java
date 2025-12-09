package Frontend;

import Backend.*;
import java.util.ArrayList;
import java.util.List;

public class Principal {

    private final Consola consola;
    private final Autenticacao autenticacao;
    private final ArrayList<Administrador> administradores;
    private final ArrayList<Treinador> treinadores;
    private List<Equipa> equipas;
    private final ArrayList<JogadorFPS> jogadoresFps;
    private final ArrayList<JogadorMOBA> jogadoresMoba;
    private final ArrayList<JogadorEfootball> jogadoresEfootball;
    private List<Torneio> torneios;
    private List<Jogador> jogadores;

    public Principal() {
        consola = new Consola();
        equipas = new ArrayList<>();
        administradores = new ArrayList<>();
        treinadores = new ArrayList<>();
        jogadoresFps = new ArrayList<>();
        jogadoresMoba = new ArrayList<>();
        jogadoresEfootball = new ArrayList<>();
        torneios = new ArrayList<>();
        jogadores = new ArrayList<>();
        autenticacao = new Autenticacao(jogadoresFps, jogadoresMoba, jogadoresEfootball, treinadores, administradores, consola);
    }

    public void iniciar() {
        carregarDados();
        String[] menuInicial = {
            " Fazer Login",
            " Criar Conta",
            "Vizualizar Ficheiro",
            " Sair"
        };

        boolean sair = false;
        while (!sair) {
            int escolha = consola.lerOpcaoMenu(menuInicial);
            switch (escolha) {
                case 1 ->
                    fazerLogin();
                case 2 ->
                    criarConta();
                case 3 ->
                    visualizarFicheiro();
                case 4 -> {
                    consola.escreverTexto("A encerrar o sistema...");
                    salvarDados();
                    sair = true;
                }
                default ->
                    consola.escreverErro("Opção inválida.");
            }
        }
    }

    private void criarConta() {
        String tipoConta = consola.lerString("Escolha o tipo de conta (Jogador FPS, Jogador MOBA, Jogador Efootball, Treinador, Administrador):");
        String nome = consola.lerString("Digite o nome completo:");
        String identificador = consola.lerString("Digite o nickname (jogador) ou o e-mail (treinador/administrador):");
        String senha = consola.lerString("Digite a palavra-passe:");

        switch (tipoConta.toLowerCase()) {
            case "jogador fps" -> {

                JogadorFPS novoJogador = new JogadorFPS(nome, identificador, senha, 0.0, 0);
                autenticacao.adicionarJogador(novoJogador); // Adicione o jogador na lista
                GestorDeFicheiros.guardarJogadores(autenticacao.getJogadores()); // Atualiza o ficheiro
                System.out.println("Jogador FPS criado: " + novoJogador.getNickname());
                

            }
            case "jogador moba" -> {
                JogadorMOBA novoJogador = new JogadorMOBA(nome, identificador, senha, "Personagem Padrão", 0, 0, 0);
                autenticacao.adicionarJogador(novoJogador); // Adicione o jogador na lista
                GestorDeFicheiros.guardarJogadores(autenticacao.getJogadores()); // Atualiza o ficheiro
                consola.escreverTexto("Jogador MOBA criado com sucesso!");
            }
            case "jogador efootball" -> {
                JogadorEfootball novoJogador = new JogadorEfootball(nome, identificador, senha, "Posição Padrão", 0, 0, 0);
                autenticacao.adicionarJogador(novoJogador); // Adicione o jogador na lista
                GestorDeFicheiros.guardarJogadores(autenticacao.getJogadores()); // Atualiza o ficheiro
                consola.escreverTexto("Jogador Efootball criado com sucesso!");
            }
            case "treinador" -> {
                Treinador novoTreinador = new Treinador(nome, identificador, senha, null);
                autenticacao.adicionarTreinador(novoTreinador);
                GestorDeFicheiros.guardarTreinadores(autenticacao.getTreinadores());
                consola.escreverTexto("Treinador criado com sucesso!");

            }
            case "administrador" -> {
                Administrador novoAdmin = new Administrador(nome, identificador, senha);
                autenticacao.adicionarAdministrador(novoAdmin); // Adicione o administrador na lista
                GestorDeFicheiros.guardarAdministradores(autenticacao.getAdministradores()); // Atualiza o ficheiro
                consola.escreverTexto("Administrador criado com sucesso!");
            }
            default ->
                consola.escreverErro("Tipo de conta inválido. Escolha entre Jogador FPS, Jogador MOBA, Jogador Efootball, Treinador ou Administrador.");
        }
    }

    private void fazerLogin() {
        String tipoConta = consola.lerString("Escolha o tipo de conta para login (Jogador FPS, Jogador MOBA, Jogador Efootball, Treinador, Administrador):");
        String identificador = consola.lerString(
                tipoConta.equals("Treinador") || tipoConta.equals("Administrador")
                ? "Digite o email:" // Solicita email para Treinador e Administrador
                : "Digite o nickname ou email:" // Para os Jogadores
        );
        String senha = consola.lerString("Digite a senha:");

        switch (tipoConta) {
            case "Jogador FPS" ->
                processarLoginJogadorFPS(identificador, senha);
            case "Jogador MOBA" ->
                processarLoginJogadorMOBA(identificador, senha);
            case "Jogador Efootball" ->
                processarLoginJogadorEfootball(identificador, senha);
            case "Treinador" ->
                processarLoginTreinador(identificador, senha); // Passa o email para o login do Treinador
            case "Administrador" ->
                processarLoginAdministrador(identificador, senha); // Passa o email para o login do Administrador
            default ->
                consola.escreverErro("Tipo de conta inválido. Certifique-se de digitar exatamente como pedido.");
        }
    }

    private void processarLoginJogadorFPS(String nickname, String senha) {
        JogadorFPS jogador = (JogadorFPS) procurarJogadorPorNickname(nickname);
        if (jogador != null) {
            verificarSenha(jogador, senha);
            menuJogadorFPS(jogador);
        } else {
            consola.escreverErro("Jogador FPS não encontrado.");
        }
        salvarDados();
    }

    private void processarLoginJogadorMOBA(String nickname, String senha) {
        JogadorMOBA jogador = (JogadorMOBA) procurarJogadorPorNickname(nickname);
        if (jogador != null) {
            verificarSenha(jogador, senha);
            menuJogadorMOBA(jogador);
        } else {
            consola.escreverErro("Jogador MOBA não encontrado.");
        }
        salvarDados();
    }

    private void processarLoginJogadorEfootball(String nickname, String senha) {
        JogadorEfootball jogador = (JogadorEfootball) procurarJogadorPorNickname(nickname);
        if (jogador != null) {
            verificarSenha(jogador, senha);
            menuJogadorEfootball(jogador);
        } else {
            consola.escreverErro("Jogador Efootball não encontrado.");
        }
        salvarDados();
    }

    private void processarLoginTreinador(String email, String senha) {
        Treinador treinador = procurarTreinadorPorEmail(email); // Busca pelo email
        if (treinador != null) {
            verificarSenha(treinador, senha); // Verifica a senha
            menuTreinador(treinador);
        } else {
            consola.escreverErro("Treinador não encontrado.");
        }
        salvarDados();
    }

    private void processarLoginAdministrador(String email, String senha) {
        Administrador administrador = procurarAdministradorPorEmail(email);
        if (administrador != null) {
            if (administrador.getSenha().equals(senha)) {
                consola.escreverTexto("Bem-vindo, Administrador " + administrador.getNomeCompleto() + "!");
                menuAdministrador(administrador);
            } else {
                consola.escreverErro("Palavra-passe incorreta.");
            }
        } else {
            consola.escreverErro("Administrador não encontrado.");
        }
        salvarDados();
    }

    private void verificarSenha(Object usuario, String senha) {
        switch (usuario) {
            case Jogador jogador -> {
                if (jogador.getSenha() == null || jogador.getSenha().equals("12345")) {
                    consola.escreverTexto("Primeiro login detetado. Por favor, configure a sua nova palavra-passe.");
                    alterarSenha(jogador);
                } else if (!jogador.getSenha().equals(senha)) {
                    consola.escreverErro("Palavra-passe incorreta.");
                    return;
                }
                consola.escreverTexto("Bem-vindo, " + jogador.getNickname() + "!");
            }
            case Treinador treinador -> {
                if (treinador.getSenha() == null || treinador.getSenha().equals("12345")) {
                    consola.escreverTexto("Primeiro login detetado. Por favor, configure a sua nova palavra-passe.");
                    alterarSenha(treinador);
                } else if (!treinador.getSenha().equals(senha)) {
                    consola.escreverErro("Palavra-passe incorreta.");
                    return;
                }
                consola.escreverTexto("Bem-vindo, Treinador " + treinador.getNomeCompleto() + "!");
            }
            default -> {
            }
        }
    }

    private void alterarSenha(Object usuario) {
        String novaSenha = consola.lerString("Digite a nova senha:");
        while (novaSenha.trim().isEmpty()) {
            consola.escreverErro("A senha não pode ser vazia. Por favor, digite novamente:");
            novaSenha = consola.lerString("Digite a nova senha:");
        }

        switch (usuario) {
            case Jogador jogador ->
                jogador.setSenha(novaSenha);
            case Treinador treinador ->
                treinador.setSenha(novaSenha);
            default -> {
            }
        }

        consola.escreverTexto("Senha alterada com sucesso!");
    }

    private void menuJogadorFPS(JogadorFPS jogador) {
        String[] opcoes = {
            "Ver Dados Pessoais",
            "Editar Dados Pessoais",
            "Ver Estatísticas",
            "Listar Torneios Inscritos",
            "Guardar Dados",
            "Carregar Dados",
            "Voltar"
        };

        boolean voltar = false;
        while (!voltar) {
            int escolha = consola.lerOpcaoMenu(opcoes);
            switch (escolha) {
                case 1 ->
                    consola.escreverTexto(jogador.getDadosPessoais());
                case 2 -> {
                    consola.escreverTexto("Editar Dados Pessoais:");
                    String novoNome = consola.lerString("Digite o novo nome:");
                    String novoNickname = consola.lerString("Digite o novo nickname:");
                    String alterarSenha = consola.lerString("Deseja alterar a sua palavra-passe? (sim/nao):");

                    if (alterarSenha.equalsIgnoreCase("sim")) {
                        alterarSenha(jogador);
                    }

                    if (!novoNome.isBlank()) {
                        jogador.setNomeCompleto(novoNome);
                    }

                    if (!novoNickname.isBlank()) {
                        if (nicknameJaExiste(novoNickname)) {
                            consola.escreverErro("O nickname '" + novoNickname + "' já está em uso. Por favor, escolha outro.");
                        } else {
                            jogador.setNickname(novoNickname);
                            consola.escreverTexto("Nickname atualizado com sucesso!");
                        }
                    }

                    consola.escreverTexto("Dados pessoais atualizados com sucesso!");
                }

                case 3 ->
                    jogador.exibirEstatisticas();
                case 4 -> {
                    List<Torneio> torneios = jogador.getTorneiosAParticipar();
                    if (torneios.isEmpty()) {
                        consola.escreverTexto("Nenhum torneio inscrito.");
                    } else {
                        consola.escreverTexto("Torneios inscritos:");
                        for (Torneio torneio : torneios) {
                            consola.escreverTexto("- " + torneio.getNome());
                        }
                    }
                }
                case 5 -> {
                    // Guardar dados do jogador FPS no ficheiro
                    jogadoresFps.add(jogador); // Adiciona o jogador à lista (se já não estiver)
                    GestorDeFicheiros.guardarEmFicheiro(jogadoresFps, "jogadoresFPS.dat");
                    consola.escreverTexto("Dados do jogador guardados com sucesso.");
                }
                case 6 -> {
                    // Carregar dados do ficheiro
                    List<JogadorFPS> jogadoresCarregados = GestorDeFicheiros.lerListaDeFicheiro("jogadoresFPS.dat", JogadorFPS.class);
                    if (jogadoresCarregados != null) {
                        jogadoresFps.clear(); // Substitui a lista atual pelos dados carregados
                        jogadoresFps.addAll(jogadoresCarregados);
                        consola.escreverTexto("Dados carregados com sucesso.");
                    } else {
                        consola.escreverErro("Erro ao carregar os dados ou ficheiro vazio.");
                    }
                }
                case 7 ->
                    voltar = true;
                default ->
                    consola.escreverErro("Opção inválida.");
            }
        }
    }

    private void menuJogadorMOBA(JogadorMOBA jogador) {
        String[] opcoes = {
            "Ver Dados Pessoais",
            "Editar Dados Pessoais",
            "Ver Estatísticas",
            "Listar Torneios Inscritos",
            "Guardar Dados",
            "Carregar Dados",
            "Voltar"
        };

        boolean voltar = false;
        while (!voltar) {
            int escolha = consola.lerOpcaoMenu(opcoes);
            switch (escolha) {
                case 1 ->
                    consola.escreverTexto(jogador.getDadosPessoais());
                case 2 -> {
                    consola.escreverTexto("Editar Dados Pessoais:");
                    String novoNome = consola.lerString("Digite o novo nome:");
                    String novoNickname = consola.lerString("Digite o novo nickname:");
                    String alterarSenha = consola.lerString("Deseja alterar a sua palavra-passe? (sim/nao):");

                    if (alterarSenha.equalsIgnoreCase("sim")) {
                        alterarSenha(jogador);
                    }

                    if (!novoNome.isBlank()) {
                        jogador.setNomeCompleto(novoNome);
                    }

                    if (!novoNickname.isBlank()) {
                        if (nicknameJaExiste(novoNickname)) {
                            consola.escreverErro("O nickname '" + novoNickname + "' já está em uso. Por favor, escolha outro.");
                        } else {
                            jogador.setNickname(novoNickname);
                            consola.escreverTexto("Nickname atualizado com sucesso!");
                        }
                    }

                    consola.escreverTexto("Dados pessoais atualizados com sucesso!");
                }

                case 3 ->
                    jogador.exibirEstatisticas();
                case 4 -> {
                    List<Torneio> torneios = jogador.getTorneiosAParticipar();
                    if (torneios.isEmpty()) {
                        consola.escreverTexto("Nenhum torneio inscrito.");
                    } else {
                        consola.escreverTexto("Torneios inscritos:");
                        for (Torneio torneio : torneios) {
                            consola.escreverTexto("- " + torneio.getNome());
                        }
                    }
                }
                case 5 -> {
                    // Guardar dados do jogador MOBA no ficheiro
                    jogadoresMoba.add(jogador); // Adiciona o jogador à lista (se já não estiver)
                    GestorDeFicheiros.guardarEmFicheiro(jogadoresMoba, "jogadoresMOBA.dat");
                    consola.escreverTexto("Dados do jogador MOBA guardados com sucesso.");
                }
                case 6 -> {
                    // Carregar dados do ficheiro
                    List<JogadorMOBA> jogadoresCarregados = GestorDeFicheiros.lerListaDeFicheiro("jogadoresMOBA.dat", JogadorMOBA.class);
                    if (!jogadoresCarregados.isEmpty()) {
                        jogadoresMoba.clear(); // Limpa a lista atual
                        jogadoresMoba.addAll(jogadoresCarregados); // Adiciona os dados carregados
                        consola.escreverTexto("Dados carregados com sucesso.");
                    } else {
                        consola.escreverErro("Erro ao carregar os dados ou ficheiro vazio.");
                    }
                }
                case 7 ->
                    voltar = true;
                default ->
                    consola.escreverErro("Opção inválida.");
            }
        }
    }

    private void menuJogadorEfootball(JogadorEfootball jogador) {
        String[] opcoes = {
            "Ver Dados Pessoais",
            "Editar Dados Pessoais",
            "Ver Estatísticas",
            "Listar Torneios Inscritos",
            "Guardar Dados",
            "Carregar Dados",
            "Voltar"
        };

        boolean voltar = false;
        while (!voltar) {
            int escolha = consola.lerOpcaoMenu(opcoes);
            switch (escolha) {
                case 1 ->
                    consola.escreverTexto(jogador.getDadosPessoais());
                case 2 -> {
                    consola.escreverTexto("Editar Dados Pessoais:");
                    String novoNome = consola.lerString("Digite o novo nome:");
                    String novoNickname = consola.lerString("Digite o novo nickname:");
                    String alterarSenha = consola.lerString("Deseja alterar a sua palavra-passe? (sim/nao):");

                    if (alterarSenha.equalsIgnoreCase("sim")) {
                        alterarSenha(jogador);
                    }

                    if (!novoNome.isBlank()) {
                        jogador.setNomeCompleto(novoNome);
                    }

                    if (!novoNickname.isBlank()) {
                        if (nicknameJaExiste(novoNickname)) {
                            consola.escreverErro("O nickname '" + novoNickname + "' já está em uso. Por favor, escolha outro.");
                        } else {
                            jogador.setNickname(novoNickname);
                            consola.escreverTexto("Nickname atualizado com sucesso!");
                        }
                    }

                    consola.escreverTexto("Dados pessoais atualizados com sucesso!");
                }

                case 3 ->
                    jogador.exibirEstatisticas();
                case 4 -> {
                    List<Torneio> torneios = jogador.getTorneiosAParticipar();
                    if (torneios.isEmpty()) {
                        consola.escreverTexto("Nenhum torneio inscrito.");
                    } else {
                        consola.escreverTexto("Torneios inscritos:");
                        for (Torneio torneio : torneios) {
                            consola.escreverTexto("- " + torneio.getNome());
                        }
                    }
                }
                case 5 -> {
                    // Guardar dados do jogador Efootball no ficheiro
                    jogadoresEfootball.add(jogador); // Adiciona o jogador à lista (se já não estiver)
                    GestorDeFicheiros.guardarEmFicheiro(jogadoresEfootball, "jogadoresEfootball.dat");
                    consola.escreverTexto("Dados do jogador Efootball guardados com sucesso.");
                }
                case 6 -> {
                    // Carregar dados do ficheiro
                    List<JogadorEfootball> jogadoresCarregados = GestorDeFicheiros.lerListaDeFicheiro("jogadoresEfootball.dat", JogadorEfootball.class);
                    if (!jogadoresCarregados.isEmpty()) {
                        jogadoresEfootball.clear(); // Limpa a lista atual
                        jogadoresEfootball.addAll(jogadoresCarregados); // Adiciona os dados carregados
                        consola.escreverTexto("Dados carregados com sucesso.");
                    } else {
                        consola.escreverErro("Erro ao carregar os dados ou ficheiro vazio.");
                    }
                }
                case 7 ->
                    voltar = true;
                default ->
                    consola.escreverErro("Opção inválida.");
            }
        }
    }

    private void menuTreinador(Treinador treinador) {
        String[] opcoes = {
            "Criar Equipa",
            "Adicionar Jogador à Equipa",
            "Remover Jogador da Equipa",
            "Editar Nome da Equipa",
            "Inscrever a Equipa em Torneio",
            "Acompanhar Resultados do Torneio",
            "Editar Dados Pessoais",
            "Guardar Dados",
            "Carregar Dados",
            "Voltar"
        };

        boolean voltar = false;
        while (!voltar) {
            int escolha = consola.lerOpcaoMenu(opcoes);
            switch (escolha) {
                case 1 -> {
                    if (treinador.getEquipa() != null) {
                        consola.escreverErro("Você já possui uma equipa associada. Não é possível criar outra.");
                    } else {
                        String nomeEquipa = consola.lerString("Digite o nome da nova equipa:");
                        Equipa novaEquipa = new Equipa(nomeEquipa, treinador);
                        treinador.setEquipa(novaEquipa); // Associa a equipa ao treinador

                    }
                }

                case 2 -> {
                    if (treinador.getEquipa() == null) {
                        consola.escreverErro("Você ainda não possui uma equipa. Crie uma antes de adicionar jogadores.");
                    } else {
                        String nomeJogador = consola.lerString("Nome completo do jogador:");
                        String nicknameJogador = consola.lerString("Nickname do jogador:");
                        Jogador jogador = procurarJogador(nomeJogador, nicknameJogador);

                        if (jogador != null) {
                            if (treinador.getEquipa().adicionarJogador(jogador)) {
                                consola.escreverTexto("Jogador " + jogador.getNickname() + " adicionado à equipa " + treinador.getEquipa().getNome() + ".");
                            } else {
                                consola.escreverErro("Jogador já pertence à equipa.");
                            }
                        } else {
                            consola.escreverErro("Jogador não encontrado.");
                        }
                    }
                }

                case 3 -> {
                    if (treinador.getEquipa() == null) {
                        consola.escreverErro("Você ainda não possui uma equipa.");
                    } else {
                        String nicknameJogador = consola.lerString("Nickname do jogador:");
                        Jogador jogador = procurarJogadorPorNickname(nicknameJogador);

                        if (jogador != null && treinador.removerJogadorEquipa(jogador)) {
                            consola.escreverTexto("Jogador " + jogador.getNickname() + " removido da equipa " + treinador.getEquipa().getNome() + ".");
                        } else {
                            consola.escreverErro("Jogador não encontrado ou não pertence à sua equipa.");
                        }
                    }
                }
                case 4 -> {
                    if (treinador.getEquipa() == null) {
                        consola.escreverErro("Você ainda não possui uma equipa.");
                    } else {
                        String novoNome = consola.lerString("Digite o novo nome da equipa:");
                        treinador.editarNomeEquipa(novoNome); // Apenas altera o nome, sem mensagens internas
                        consola.escreverTexto("Nome da equipa atualizado para: " + novoNome);
                    }
                }

                case 5 -> {
                    if (treinador.getEquipa() == null) {
                        consola.escreverErro("Você ainda não possui uma equipa para inscrever.");
                    } else {
                        String nomeTorneio = consola.lerString("Digite o nome do torneio para inscrever a equipa:");
                        Torneio torneio = procurarTorneio(nomeTorneio);
                        if (torneio != null) {
                            try {
                                treinador.inscreverEquipa(torneio); // Método que apenas realiza a lógica
                                consola.escreverTexto("Equipa '" + treinador.getEquipa().getNome() + "' inscrita no torneio '" + torneio.getNome() + "' com sucesso!");
                            } catch (IllegalArgumentException e) {
                                consola.escreverErro(e.getMessage());
                            }
                        } else {
                            consola.escreverErro("Torneio não encontrado.");
                        }
                    }
                }

                case 6 -> {
                    if (treinador.getEquipa() == null) {
                        consola.escreverErro("Você ainda não possui uma equipa.");
                    } else {
                        String nomeTorneio = consola.lerString("Digite o nome do torneio:");
                        Torneio torneio = procurarTorneio(nomeTorneio);

                        if (torneio != null) {
                            treinador.acompanharResultadosTorneio(torneio);
                        } else {
                            consola.escreverErro("Torneio não encontrado.");
                        }
                    }
                }
                case 7 -> {
                    consola.escreverTexto("Editar Dados Pessoais:");
                    String novoNome = consola.lerString("Digite o novo nome:");
                    String alterarSenha = consola.lerString("Deseja alterar a sua palavra-passe? (sim/nao):");
                    if (alterarSenha.equalsIgnoreCase("sim")) {
                        alterarSenha(treinador);
                    }

                    if (!novoNome.isBlank()) {
                        treinador.setNomeCompleto(novoNome);
                    }

                    consola.escreverTexto("Dados pessoais atualizados com sucesso!");
                }

                case 8 -> {
                    // Guardar dados do treinador no ficheiro
                    treinadores.add(treinador); // Adiciona o treinador à lista (se já não estiver)
                    GestorDeFicheiros.guardarEmFicheiro(treinadores, "treinadores.dat");
                    consola.escreverTexto("Dados do treinador guardados com sucesso.");
                }
                case 9 -> {
                    // Carregar dados do ficheiro
                    List<Treinador> treinadoresCarregados = GestorDeFicheiros.lerListaDeFicheiro("treinadores.dat", Treinador.class);
                    if (!treinadoresCarregados.isEmpty()) {
                        treinadores.clear(); // Limpa a lista atual
                        treinadores.addAll(treinadoresCarregados); // Adiciona os dados carregados
                        consola.escreverTexto("Dados carregados com sucesso.");
                    } else {
                        consola.escreverErro("Erro ao carregar os dados ou ficheiro vazio.");
                    }
                }
                case 10 ->
                    voltar = true;
                default ->
                    consola.escreverErro("Opção inválida.");
            }
        }
    }

    private void menuAdministrador(Administrador administrador) {
        String[] opcoes = {
            "Criar Torneio",
            "Remover Torneio",
            "Adicionar Jogador",
            "Remover Jogador",
            "Adicionar Treinador",
            "Remover Treinador",
            "Agendar Partida",
            "Registar Resultado de Partida",
            "Acompanhar Estatísticas do Torneio",
            "Guardar Dados",
            "Carregar Dados",
            "Voltar"
        };

        boolean voltar = false;
        while (!voltar) {
            int escolha = consola.lerOpcaoMenu(opcoes);
            switch (escolha) {
                case 1 -> {
                    String nomeTorneio = consola.lerString("Digite o nome do torneio:");
                    String tipoJogo = consola.lerString("Digite o tipo de jogo (FPS, MOBA, eFootball):");
                    if (tipoJogo.equalsIgnoreCase("FPS") || tipoJogo.equalsIgnoreCase("MOBA") || tipoJogo.equalsIgnoreCase("eFootball")) {
                        administrador.criarTorneio(nomeTorneio, tipoJogo);
                        consola.escreverTexto("Torneio \"" + nomeTorneio + "\" do tipo " + tipoJogo + " criado com sucesso!");
                    } else {
                        consola.escreverErro("Tipo de jogo inválido. Apenas FPS, MOBA ou eFootball são permitidos.");
                    }

                }
                case 2 -> {
                    String nomeTorneio = consola.lerString("Digite o nome do torneio:");
                    Torneio torneio = procurarTorneio(nomeTorneio);
                    if (torneio != null) {
                        administrador.removerTorneio(torneio);
                    } else {
                        consola.escreverErro("Torneio não encontrado.");
                    }
                }
                case 3 -> {
                    String tipoJogador = consola.lerString("Digite o tipo de jogador (FPS, MOBA, Efootball):");
                    String nome = consola.lerString("Digite o nome completo do jogador:");
                    String nickname = consola.lerString("Digite o nickname do jogador:");

                    if (nicknameJaExiste(nickname)) {
                        consola.escreverErro("O nickname '" + nickname + "' já está em uso. Por favor, escolha outro.");
                        return;
                    }

                    switch (tipoJogador) {
                        case "FPS" -> {
                            JogadorFPS jogador = new JogadorFPS(nome, nickname, "12345", 0.0, 0);
                            if (!jogadoresFps.contains(jogador)) {
                                jogadoresFps.add(jogador);
                                consola.escreverTexto("Jogador FPS adicionado com sucesso. A palavra-passe padrão é '12345'.");
                            } else {
                                consola.escreverErro("Jogador FPS já registrado.");
                            }
                        }
                        case "MOBA" -> {
                            JogadorMOBA jogador = new JogadorMOBA(nome, nickname, "12345", "Personagem Padrão", 0, 0, 0);
                            if (!jogadoresMoba.contains(jogador)) {
                                jogadoresMoba.add(jogador);
                                consola.escreverTexto("Jogador MOBA adicionado com sucesso. A palavra-passe padrão é '12345'.");
                            } else {
                                consola.escreverErro("Jogador MOBA já registrado.");
                            }
                        }
                        case "Efootball" -> {
                            JogadorEfootball jogador = new JogadorEfootball(nome, nickname, "12345", "Posição Padrão", 0, 0, 0);
                            if (!jogadoresEfootball.contains(jogador)) {
                                jogadoresEfootball.add(jogador);
                                consola.escreverTexto("Jogador Efootball adicionado com sucesso. A palavra-passe padrão é '12345'.");
                            } else {
                                consola.escreverErro("Jogador Efootball já registrado.");
                            }
                        }
                        default ->
                            consola.escreverErro("Tipo de jogador inválido. Use FPS, MOBA ou Efootball.");
                    }
                }

                case 4 -> {
                    String tipoJogador = consola.lerString("Digite o tipo de jogador a ser removido (FPS, MOBA, Efootball):");
                    String nickname = consola.lerString("Digite o nickname do jogador:");

                    switch (tipoJogador) {
                        case "FPS" -> {
                            JogadorFPS jogador = (JogadorFPS) procurarJogadorPorNickname(nickname);
                            if (jogador != null && jogadoresFps.contains(jogador)) {
                                jogadoresFps.remove(jogador);
                                consola.escreverTexto("Jogador FPS removido com sucesso.");
                            } else {
                                consola.escreverErro("Jogador FPS não encontrado.");
                            }
                        }
                        case "MOBA" -> {
                            JogadorMOBA jogador = (JogadorMOBA) procurarJogadorPorNickname(nickname);
                            if (jogador != null && jogadoresMoba.contains(jogador)) {
                                jogadoresMoba.remove(jogador);
                                consola.escreverTexto("Jogador MOBA removido com sucesso.");
                            } else {
                                consola.escreverErro("Jogador MOBA não encontrado.");
                            }
                        }
                        case "Efootball" -> {
                            JogadorEfootball jogador = (JogadorEfootball) procurarJogadorPorNickname(nickname);
                            if (jogador != null && jogadoresEfootball.contains(jogador)) {
                                jogadoresEfootball.remove(jogador);
                                consola.escreverTexto("Jogador de Efootball removido com sucesso.");
                            } else {
                                consola.escreverErro("Jogador de Efootball não encontrado.");
                            }
                        }
                        default ->
                            consola.escreverErro("Tipo de jogador inválido. Certifique-se de digitar exatamente como pedido (FPS, MOBA, Efootball).");
                    }
                }

                case 5 -> {
                    String nomeCompleto = consola.lerString("Digite o nome completo do treinador:");
                    String email = consola.lerString("Digite o email do treinador:");

                    if (procurarTreinadorPorEmail(email) != null) {
                        consola.escreverErro("Já existe um treinador com este email.");
                        return;
                    }

                    String tipoTreinadorInput = consola.lerString("Digite o tipo de treinador (FPS, MOBA, eFootball):");
                    TipoTreinador tipoTreinador;
                    switch (tipoTreinadorInput.toUpperCase()) {
                        case "FPS" ->
                            tipoTreinador = TipoTreinador.FPS;
                        case "MOBA" ->
                            tipoTreinador = TipoTreinador.MOBA;
                        case "EFOOTBALL" ->
                            tipoTreinador = TipoTreinador.EFOOTBALL;
                        default -> {
                            consola.escreverErro("Tipo de treinador inválido. Escolha entre FPS, MOBA ou EFOOTBALL.");
                            return;
                        }
                    }

                    Treinador novoTreinador = new Treinador(nomeCompleto, email, "12345", tipoTreinador);
                    treinadores.add(novoTreinador);

                    consola.escreverTexto("Treinador adicionado com sucesso. O tipo é " + tipoTreinador + ".");
                    consola.escreverTexto("A senha padrão é '12345'. O treinador deve alterá-la no primeiro login.");
                }

                case 6 -> {
                    String email = consola.lerString("Digite o e-mail do treinador:");
                    Treinador treinador = procurarTreinadorPorEmail(email);
                    if (treinador != null) {
                        administrador.removerTreinador(treinadores, treinador);
                    } else {
                        consola.escreverErro("Treinador não encontrado.");
                    }
                }
                case 7 -> {
                    String nomeTorneio = consola.lerString("Digite o nome do torneio:");
                    Torneio torneio = procurarTorneio(nomeTorneio);

                    if (torneio != null) {
                        // Exibe as equipas inscritas no torneio
                        List<Equipa> equipasInscritas = torneio.getEquipas();
                        if (equipasInscritas.isEmpty()) {
                            consola.escreverTexto("Nenhuma equipa está inscrita neste torneio.");
                            break;
                        } else {
                            consola.escreverTexto("Equipas inscritas no torneio:");
                            for (Equipa equipa : equipasInscritas) {
                                consola.escreverTexto("- " + equipa.getNome());
                            }
                        }

                        // Solicita as equipas para a partida
                        String nomeEquipaA = consola.lerString("Digite o nome da equipa A:");
                        String nomeEquipaB = consola.lerString("Digite o nome da equipa B:");
                        Equipa equipaA = procurarEquipa(nomeEquipaA);
                        Equipa equipaB = procurarEquipa(nomeEquipaB);

                        if (equipaA == null || equipaB == null) {
                            consola.escreverErro("Uma ou ambas as equipas não foram encontradas.");
                        } else if (!equipasInscritas.contains(equipaA) || !equipasInscritas.contains(equipaB)) {
                            consola.escreverErro("Ambas as equipas devem estar inscritas no torneio.");
                        } else {
                            administrador.agendarPartida(torneio, equipaA, equipaB, 0, 0);

                        }
                    } else {
                        consola.escreverErro("Torneio não encontrado.");
                    }
                }

                case 8 -> {
                    String nomeTorneio = consola.lerString("Digite o nome do torneio:");
                    Torneio torneio = procurarTorneio(nomeTorneio);

                    if (torneio != null) {
                        // Listar as partidas do torneio
                        if (!torneio.getPartidas().isEmpty()) {
                            consola.escreverTexto("Partidas do torneio:");
                            for (Partida partida : torneio.getPartidas()) {
                                consola.escreverTexto("ID: " + partida.getId()
                                        + ", Equipa A: " + partida.getEquipaA().getNome()
                                        + ", Equipa B: " + partida.getEquipaB().getNome());
                            }

                            int idPartida = consola.lerInteiro("Digite o ID da partida:");
                            Partida partida = procurarPartidaPorId(torneio, idPartida);

                            if (partida != null) {
                                int pontosA = consola.lerInteiro("Digite os pontos da equipa A:");
                                int pontosB = consola.lerInteiro("Digite os pontos da equipa B:");

                                administrador.registarResultado(torneio, partida, pontosA, pontosB);

                            } else {
                                consola.escreverErro("Partida com o ID fornecido não encontrada.");
                            }
                        } else {
                            consola.escreverTexto("Não há partidas disponíveis neste torneio.");
                        }
                    } else {
                        consola.escreverErro("Torneio não encontrado.");
                    }

                }

                case 9 -> {
                    String nomeTorneio = consola.lerString("Digite o nome do torneio:");
                    Torneio torneio = procurarTorneio(nomeTorneio);
                    if (torneio != null) {
                        administrador.acompanharEstatisticasTorneio(torneio);
                    } else {
                        consola.escreverErro("Torneio não encontrado.");
                    }
                }
                case 10 -> {
                    // Guardar todos os dados no ficheiro
                    GestorDeFicheiros.guardarEmFicheiro(torneios, "torneios.dat");
                    GestorDeFicheiros.guardarEmFicheiro(jogadores, "jogadores.dat");
                    GestorDeFicheiros.guardarEmFicheiro(treinadores, "treinadores.dat");
                    GestorDeFicheiros.guardarEmFicheiro(administradores, "administradores.dat");

                }

                case 11 -> {
                    // Carregar todos os dados do ficheiro
                    List<Torneio> torneiosCarregados = GestorDeFicheiros.lerListaDeFicheiro("torneios.dat", Torneio.class);
                    List<Jogador> jogadoresCarregados = GestorDeFicheiros.lerListaDeFicheiro("jogadores.dat", Jogador.class);
                    List<Treinador> treinadoresCarregados = GestorDeFicheiros.lerListaDeFicheiro("treinadores.dat", Treinador.class);
                    List<Administrador> administradoresCarregados = GestorDeFicheiros.lerListaDeFicheiro("administradores.dat", Administrador.class);

                    if (!torneiosCarregados.isEmpty()) {
                        torneios.clear();
                    }
                    torneios.addAll(torneiosCarregados);
                    if (!jogadoresCarregados.isEmpty()) {
                        jogadores.clear();
                    }
                    jogadores.addAll(jogadoresCarregados);
                    if (!treinadoresCarregados.isEmpty()) {
                        treinadores.clear();
                    }
                    treinadores.addAll(treinadoresCarregados);
                    if (!administradoresCarregados.isEmpty()) {
                        administradores.clear();
                    }
                    administradores.addAll(administradoresCarregados);

                    consola.escreverTexto("Dados carregados com sucesso.");
                }

                case 12 ->
                    voltar = true;
                default ->
                    consola.escreverErro("Opção inválida.");
            }
        }
    }

    // Método para procurar um jogador por nome completo e nickname
    private Jogador procurarJogador(String nomeCompleto, String nickname) {
        // Procurar em jogadores FPS
        for (JogadorFPS jogador : jogadoresFps) {
            if (jogador.getNomeCompleto().equals(nomeCompleto) && jogador.getNickname().equals(nickname)) {
                return jogador;
            }
        }
        // Procurar em jogadores MOBA
        for (JogadorMOBA jogador : jogadoresMoba) {
            if (jogador.getNomeCompleto().equals(nomeCompleto) && jogador.getNickname().equals(nickname)) {
                return jogador;
            }
        }
        // Procurar em jogadores Efootball
        for (JogadorEfootball jogador : jogadoresEfootball) {
            if (jogador.getNomeCompleto().equals(nomeCompleto) && jogador.getNickname().equals(nickname)) {
                return jogador;
            }
        }
        // Se não encontrar, retorna null
        return null;
    }

// Procurar jogador por nickname
    private Jogador procurarJogadorPorNickname(String nickname) {
        for (Jogador jogador : jogadoresFps) {
            if (jogador.getNickname().equalsIgnoreCase(nickname)) {
                return jogador;
            }
        }
        for (Jogador jogador : jogadoresMoba) {
            if (jogador.getNickname().equalsIgnoreCase(nickname)) {
                return jogador;
            }
        }
        for (Jogador jogador : jogadoresEfootball) {
            if (jogador.getNickname().equalsIgnoreCase(nickname)) {
                return jogador;
            }
        }
        return null;
    }

    //procurar admin por email
    private Administrador procurarAdministradorPorEmail(String email) {
        for (Administrador administrador : administradores) {
            if (administrador.getEmail().equals(email)) {
                return administrador;
            }
        }
        return null;
    }

// Procurar treinador por email
    private Treinador procurarTreinadorPorEmail(String email) {
        for (Treinador treinador : treinadores) {
            if (treinador.getEmail().equalsIgnoreCase(email)) {
                return treinador;
            }
        }
        return null;
    }

// Procurar torneio por nome
    private Torneio procurarTorneio(String nome) {
        for (Administrador admin : administradores) {
            for (Torneio torneio : admin.getTorneios()) {
                if (torneio.getNome().equals(nome)) {
                    return torneio;
                }
            }
        }
        return null;
    }

// Procurar equipa por nome
    private Equipa procurarEquipa(String nome) {
        for (Treinador treinador : treinadores) {
            Equipa equipa = treinador.getEquipa();
            if (equipa != null && equipa.getNome().equals(nome)) {
                return equipa;
            }
        }
        return null;
    }

    private Partida procurarPartidaPorId(Torneio torneio, int id) {
        for (Partida partida : torneio.getPartidas()) {
            if (partida.getId() == id) {
                return partida;
            }
        }
        return null; // Caso o ID não corresponda a nenhuma partida
    }

    private boolean nicknameJaExiste(String nickname) {
        // Verificar em Jogadores FPS
        for (Jogador jogador : jogadoresFps) {
            if (jogador.getNickname().equalsIgnoreCase(nickname)) {
                return true;
            }
        }
        // Verificar em Jogadores MOBA
        for (Jogador jogador : jogadoresMoba) {
            if (jogador.getNickname().equalsIgnoreCase(nickname)) {
                return true;
            }
        }
        // Verificar em Jogadores Efootball
        for (Jogador jogador : jogadoresEfootball) {
            if (jogador.getNickname().equalsIgnoreCase(nickname)) {
                return true;
            }
        }

        return false;
    }

    private void carregarDados() {
        consola.escreverTexto("A carregar dados armazenados...");
        equipas = GestorDeFicheiros.lerEquipas();
        torneios = GestorDeFicheiros.lerTorneios();
        jogadores = GestorDeFicheiros.lerJogadores();
        if (equipas == null) {
            equipas = new ArrayList<>();
            consola.escreverTexto("Nenhuma equipa encontrada, inicializando lista vazia.");
        }
        if (torneios == null) {
            torneios = new ArrayList<>();
            consola.escreverTexto("Nenhum torneio encontrado, inicializando lista vazia.");
        }
        if (jogadores == null) { // Se não encontrar dados, inicializa a lista
            jogadores = new ArrayList<>();
            consola.escreverTexto("Nenhum jogador encontrado, inicializando lista vazia.");
        }
        consola.escreverTexto("Dados carregados com sucesso!");
    }

    private void visualizarFicheiro() {
        String caminhoFicheiro = consola.lerString("Digite o nome do ficheiro a ser visualizado: jogadores.dat, treinadores.dat, administradores.dat, torneios.dat ou equipas.dat");

        Class<?> tipoClasse = null;
        switch (caminhoFicheiro) {
            case "jogadores.dat" ->
                tipoClasse = Jogador.class;
            case "treinadores.dat" ->
                tipoClasse = Treinador.class;
            case "administradores.dat" ->
                tipoClasse = Administrador.class;
            case "torneios.dat" ->
                tipoClasse = Torneio.class;
            case "equipas.dat" ->
                tipoClasse = Equipa.class;
            default -> {
                consola.escreverErro("Ficheiro não reconhecido. Certifique-se de digitar o nome corretamente.");
                return; // Sai do método se o ficheiro não for reconhecido
            }
        }

        GestorDeFicheiros.visualizarDadosDeFicheiro(caminhoFicheiro, tipoClasse);
    }

    private void salvarDados() {
        consola.escreverTexto("Salvando dados...");
        GestorDeFicheiros.guardarEquipas(equipas);
        GestorDeFicheiros.guardarTorneios(torneios);
        GestorDeFicheiros.guardarAdministradores(administradores);
        GestorDeFicheiros.guardarTreinadores(treinadores);
        jogadores.forEach(j -> {
            if (j instanceof JogadorFPS) {
                GestorDeFicheiros.guardarEmFicheiro(jogadoresFps, "jogadoresFPS.dat");
            } else if (j instanceof JogadorMOBA) {
                GestorDeFicheiros.guardarEmFicheiro(jogadoresMoba, "jogadoresMOBA.dat");
            } else if (j instanceof JogadorEfootball) {
                GestorDeFicheiros.guardarEmFicheiro(jogadoresEfootball, "jogadoresEfootball.dat");
            }
        });
        consola.escreverTexto("Dados salvos com sucesso!");
    }

    public static void main(String[] args) {
        new Principal().iniciar();
    }
}
