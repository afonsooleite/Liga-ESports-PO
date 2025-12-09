# Gestão de Liga de e-Sports – Projeto de PO

Este repositório contém o projeto desenvolvido na unidade curricular **Programação com Objetos (PO)**, da Licenciatura em Engenharia e Gestão de Sistemas de Informação – Universidade do Minho.

O objetivo deste projeto é implementar um **sistema completo de gestão de uma liga de e-sports** com suporte a jogadores, equipas, torneios e partidas, seguindo os princípios fundamentais da programação orientada a objetos.

---

## Funcionalidades Principais

✔ Registo e autenticação de utilizadores  
✔ Gestão de equipas, jogadores e treinadores  
✔ Três tipos de jogadores com atributos e cálculos específicos:  
&nbsp;&nbsp;&nbsp;🎯 FPS · ⚽ eFootball · 🛡 MOBA  
✔ Criação e gestão de torneios com partidas  
✔ Persistência de dados em ficheiros binários  
✔ Interface de consola com menus interativos  
✔ Estatísticas individuais e coletivas  
✔ Administração do sistema (gestão completa)

---

## Arquitetura e Classes

O sistema utiliza **herança**, **polimorfismo** e **encapsulamento**, organizando as classes em:

| Camada | Classes |
|--------|---------|
| Core (Domínio) | Equipa, Jogador (base), JogadorFPS, JogadorMOBA, JogadorEfootball, Treinador |
| Gestão da Aplicação | Torneio, Partida, Administrador |
| Infraestrutura | Consola, Principal, Autenticacao, GestorDeFicheiros, TipoTreinador |

O método `main()` encontra-se na classe **Principal**.

---

## Estrutura do Repositório

```text
Liga-ESports-PO/
│ README.md          # Este ficheiro
│ LICENSE            # MIT License
│
├── src/             # Código-fonte em Java
│   *.java
│
└── docs/
    Relatorio_PO.pdf # Documentação académica do projeto
