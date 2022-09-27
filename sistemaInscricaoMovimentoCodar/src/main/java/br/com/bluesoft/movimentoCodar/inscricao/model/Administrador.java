package br.com.bluesoft.movimentoCodar.inscricao.model;

import br.com.bluesoft.movimentoCodar.inscricao.Sistema;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Administrador {
    public static long quantidadePerguntas() throws IOException {
        Path file = Paths.get("formulario.txt");
        return Files.lines(file).count();
    }

    public static void adicionaPergunta() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("formulario.txt", true));
        Scanner inputNovaPergunta = new Scanner(System.in);
        System.out.println("Digite a nova pergunta:");
        String novaPergunta = inputNovaPergunta.nextLine();

        if(Candidato.getPerguntasAdicionais().contains(novaPergunta)) {
            return;
        }
        bw.newLine();
        bw.write(novaPergunta);
        bw.close();
        adicionaPerguntaNaLista(novaPergunta);
        Sistema.menu();
    }

    public static void adicionaPerguntaNaLista(String pergunta) {
        Candidato.getPerguntasAdicionais().add(pergunta);
    }

    public static void exibirCandidato() throws IOException {
        Scanner inputExibirCandidato = new Scanner(System.in);
        System.out.println("""
                ----------- FORMULÁRIO DE INSCRIÇÃO DO MOVIMENTO CODAR ------------
                -------------------------------------------------------------------
                |  Selecione uma opção:                                           |
                |  1 - Exibir todos os candidatos                                 |
                |  2 - Exibir quantidade de candidatos por idade                  |
                -------------------------------------------------------------------
                """);
        int opcao = inputExibirCandidato.nextInt();

        switch (opcao) {
            case 1 -> Administrador.exibirTodosCandidato();
            case 2 -> Administrador.mostrarIdades();
            default -> System.out.println("Digite uma opção válida");
        }
    }

    public static void exibirTodosCandidato () throws IOException {
       Candidato.getListaDeCandidatos().sort(Comparator.comparing(Candidato::getIdade));
       Candidato.getListaDeCandidatos().forEach((Candidato candidato) ->
               System.out.println("Nome: " + candidato.getNome() + ", Idade: " + candidato.getIdade()));
       Sistema.menu();
    }

    public static void buscaCandidato() throws IOException {
        Scanner inputBuscaCandidato = new Scanner(System.in);
        System.out.println("Digite o nome do candidato:");
        String nomeCandidato = inputBuscaCandidato.nextLine();
        System.out.println("Digite o email do candidato:");
        String emailCandidato = inputBuscaCandidato.nextLine();

        Candidato.getListaDeCandidatos().stream()
                .filter(candidato -> Objects.equals(candidato.getNome(), nomeCandidato) && Objects.equals(candidato.getEmail(), emailCandidato))
                .forEach(candidato -> System.out.println("Candidato existe"));
        Sistema.menu();
    }

    public static void mostrarIdades() throws IOException {
        for (Map.Entry<Integer, Integer> entry : Candidato.getQuantidadeIdade().entrySet()) {
            System.out.println("Idade: " + entry.getKey() + "\tQuantidade: " + entry.getValue());
        }
        Sistema.menu();
    }

    public static void validaCandidatos() {
        Candidato.getListaDeCandidatos().sort(new Comparator<Candidato>() {
            @Override
            public int compare(Candidato candidato1, Candidato candidato2) {
                String nome1 = candidato1.getNome();
                String nome2 = candidato2.getNome();
                String email1 = candidato1.getEmail();
                String email2 = candidato2.getEmail();
                int resultadoNome = nome1.compareTo(nome2);
                int resultadoEmail = email1.compareTo(email2);
                if(resultadoNome == 0 && resultadoEmail == 0) {
                    Candidato.getListaDeCandidatosDuplicados().add(candidato1);
                    Candidato.getListaDeCandidatosDuplicados().add(candidato2);
                    return 0;
                }
                return 1;
            }
        });
        Candidato.exibirCandidatosDuplicados();
    }

    public static void escolherPerguntaExcluir() throws IOException {
        Scanner inputEscolherPergunta = new Scanner(System.in);
        System.out.println("PERGUNTAS QUE PODEM SER EXCLUÍDAS");

        if(Candidato.getPerguntasAdicionais().size() == 0) {
            System.out.println("Adicione uma pergunta antes");
            return;
        }

        for (int i = 0; i < Candidato.getPerguntasAdicionais().size(); i++) {
            System.out.println( i + "-" + Candidato.getPerguntasAdicionais().get(i));
        }

        System.out.println("-------------------------");
        System.out.println("Digite  o número que corresponde a pergunta");
        int resposta = inputEscolherPergunta.nextInt();
        String perguntaSelecionada = Candidato.getPerguntasAdicionais().get(resposta);
        excluirPergunta(perguntaSelecionada);
        Sistema.menu();
    }

    public static void excluirPergunta(String resposta) {
        File file = new File("formulario.txt");

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String linha = br.readLine();
            ArrayList<String> salvar = new ArrayList<>();

            while (linha!= null) {
                if(!linha.equals(resposta)) {
                    salvar.add(linha);
                }
                linha = br.readLine();
            }
            br.close();
            fr.close();
            FileWriter fw2 = new FileWriter(file, true);
            fw2.close();

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i =0; i < salvar.size(); i++) {
                bw.write(salvar.get(i));
                bw.newLine();
            }
            bw.close();
            fw.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}