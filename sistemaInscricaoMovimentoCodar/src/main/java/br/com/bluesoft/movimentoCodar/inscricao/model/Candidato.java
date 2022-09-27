package br.com.bluesoft.movimentoCodar.inscricao.model;

import br.com.bluesoft.movimentoCodar.inscricao.Sistema;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Candidato {
    private static int contador = 1;
    private int id;
    private String nome;
    private String email;
    private Integer idade;
    private String telefone;
    private List<String> respostasAdicionais = new ArrayList<>();
    private static List<String> perguntasAdicionais = new ArrayList<>();
    private static List<Candidato> listaDeCandidatos = new ArrayList<>();
    private static List<Candidato> listaDeCandidatosDuplicados = new ArrayList<>();
    private static Map<Integer, Integer> quantidadeIdade = new HashMap<>();

    public Candidato(String nome, String email, Integer idade, String telefone) {
        this.id += Candidato.contador;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.telefone = telefone;
        Candidato.contador +=1;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<String> getRespostasAdicionais() {
        return respostasAdicionais;
    }

    public static List<String> getPerguntasAdicionais() {
        return perguntasAdicionais;
    }

    public static int getUltimoCandidato() {
        return listaDeCandidatos.size() -1;
    }

    public static List<Candidato> getListaDeCandidatos() {
        return listaDeCandidatos;
    }

    public static List<Candidato> getListaDeCandidatosDuplicados() {
        return listaDeCandidatosDuplicados;
    }

    public static Map<Integer, Integer> getQuantidadeIdade() {
        return quantidadeIdade;
    }

    public static void adicionaCandidatoNaLista(Candidato candidato) {
        listaDeCandidatos.add(candidato);
    }

    public static void cadastraCandidato() throws IOException {
        Scanner lerPergunta = new Scanner(new FileReader("formulario.txt"));
        Scanner inputRespostas = new Scanner(System.in);
        while (lerPergunta.hasNext()) {
            String perguntaNome = lerPergunta.nextLine();
            System.out.println(perguntaNome);
            String respostaNome = inputRespostas.nextLine();
            String perguntaEmail = lerPergunta.nextLine();
            System.out.println(perguntaEmail);
            String respostaEmail = inputRespostas.nextLine();
            String perguntaIdade = lerPergunta.nextLine();
            System.out.println(perguntaIdade);
            Integer respostaIdade = inputRespostas.nextInt();
            String perguntaTelefone = lerPergunta.nextLine();
            System.out.println(perguntaTelefone);
            String respostaTelefone = inputRespostas.next();

            if(respostaIdade < 16) {
                System.out.println("Obrigado pelo interesse, mas é necessário ter mais de 15 anos");
                return;
            }

            Candidato.adicionaCandidatoNaLista(new Candidato(respostaNome,respostaEmail, respostaIdade, respostaTelefone));

            if(quantidadeIdade.containsKey(respostaIdade)){
                quantidadeIdade.put(respostaIdade, quantidadeIdade.get(respostaIdade) + 1);
            }else{
                quantidadeIdade.put(respostaIdade, 1);
            }

            if(Administrador.quantidadePerguntas() > 4) {
                Long perguntasAdicionais = Administrador.quantidadePerguntas() - 4;
                for (int i = 1; i <= perguntasAdicionais ; i++) {
                    String pergunta = lerPergunta.nextLine();
                    System.out.println(pergunta);
                    listaDeCandidatos.get(Candidato.getUltimoCandidato()).respostasAdicionais.add(inputRespostas.next());
                }
            }
        }
        gerarFormularios();
        Sistema.menu();
    }

    public static void gerarFormularios() throws IOException {
        String nome = listaDeCandidatos.get(Candidato.getUltimoCandidato()).getNome();
        String nomeFormatado = nome.toUpperCase().trim().replace(" ", "");
        Integer id = listaDeCandidatos.get(Candidato.getUltimoCandidato()).getId();
        PrintWriter pw = new PrintWriter(new FileWriter(id+"-"+nomeFormatado + ".txt"));
        pw.println(nome);
        pw.println(listaDeCandidatos.get(Candidato.getUltimoCandidato()).getEmail());
        pw.println(listaDeCandidatos.get(Candidato.getUltimoCandidato()).getIdade());
        pw.println(listaDeCandidatos.get(Candidato.getUltimoCandidato()).getTelefone());
        listaDeCandidatos.stream().forEach(candidato -> pw.println(candidato.getRespostasAdicionais()));
        pw.close();
    }

    public static void exibirCandidatosDuplicados() {
        System.out.println(getListaDeCandidatosDuplicados());
    }

    @Override
    public int hashCode() {
        return this.nome.charAt(0);
    }

    @Override
    public String toString() {
        return "Id: "+ getId() + " Nome: " + getNome() + " Idade: " + getIdade();
    }
}