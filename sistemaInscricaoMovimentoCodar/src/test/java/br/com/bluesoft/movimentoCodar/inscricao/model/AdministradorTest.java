package br.com.bluesoft.movimentoCodar.inscricao.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorTest {

    @BeforeEach
    public void beforeEach() {
        Administrador.adicionaPerguntaNaLista("Já estudou programação?");
    }

    @Test
    void verificaSePerguntaFoiAdicionadaNaLista() {
        assertFalse(Candidato.getPerguntasAdicionais().isEmpty());
    }

    @Test
    void verificaSeaPerguntaEaMesma() {
        assertEquals("Já estudou programação?",Candidato.getPerguntasAdicionais().get(0));
    }
}