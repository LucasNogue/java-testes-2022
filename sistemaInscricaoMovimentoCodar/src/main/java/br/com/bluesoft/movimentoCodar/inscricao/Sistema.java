package br.com.bluesoft.movimentoCodar.inscricao;

import br.com.bluesoft.movimentoCodar.inscricao.model.Administrador;
import br.com.bluesoft.movimentoCodar.inscricao.model.Candidato;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Sistema {
    public static void main(String[] args) throws IOException {
        try {
            Sistema.menu();
        }catch (InputMismatchException e){
            System.out.println("Digite uma opção válida");
            menu();
        }
    }
    public static void menu() throws IOException {
        Scanner inputMenu = new Scanner(System.in);
        System.out.println("""
                ----------- FORMULÁRIO DE INSCRIÇÃO DO MOVIMENTO CODAR ------------
                -------------------------------------------------------------------
                |  Selecione uma opção:                                           |
                |  1 - Candidatar-se                                              |
                |  2 - Adicionar pergunta ao formulário                           |
                |  3 - Remover pergunta do formulário                             |
                |  4 - Listar formulários cadastrados                             |
                |  5 - Pesquisar formulários cadastrados                          |
                |  6 - Validar formulários                                        |
                |  7 - Sair                                                       |
                -------------------------------------------------------------------
                """);
        int opcao = inputMenu.nextInt();

        switch (opcao) {
            case 1 -> Candidato.cadastraCandidato();
            case 2 -> Administrador.adicionaPergunta();
            case 3 -> Administrador.escolherPerguntaExcluir();
            case 4 -> Administrador.exibirCandidato();
            case 5 -> Administrador.buscaCandidato();
            case 6 -> Administrador.validaCandidatos();
            case 7 -> System.exit(0);
            default -> System.out.println("Digite uma opção válida");
        }
    }
}