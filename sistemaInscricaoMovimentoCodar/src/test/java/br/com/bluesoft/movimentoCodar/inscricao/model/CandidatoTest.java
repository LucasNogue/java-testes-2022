package br.com.bluesoft.movimentoCodar.inscricao.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CandidatoTest {

    @BeforeEach
    public void beforeEach() {
        Candidato.adicionaCandidatoNaLista(new Candidato("Lucas Nogueira", "lucas@gmail.com",19,"13997360295"));
    }

    @Test
    void deveriaAdicionarUmCandidatoNaListaDeCandidatos() {
        Candidato mock = Mockito.mock(Candidato.class);
        assertFalse(mock.getListaDeCandidatos().isEmpty());
    }

    @Test
    void verificaSeOcandidatoEoMesmo() {
        Candidato candidato = Candidato.getListaDeCandidatos().get(0);
        assertEquals("Lucas Nogueira",candidato.getNome());
        assertEquals("lucas@gmail.com",candidato.getEmail());
    }
}