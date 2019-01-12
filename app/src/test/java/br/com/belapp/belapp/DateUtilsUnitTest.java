package br.com.belapp.belapp;

import org.junit.Test;

import static org.junit.Assert.*;


import br.com.belapp.belapp.utils.DateUtils;

public class DateUtilsUnitTest {

    @Test
    public void verificarSomaDiasComDataEspecifica(){
        String data = "01/09/2018";
        int dias1 = 6;
        int dias2 = 30;
        int dias3 = 365;
        String dataEsperada1 = "07/09/2018";
        String dataEsperada2 = "01/10/2018";
        String dataEsperada3 = "01/09/2019";

        // verifica soma dentro do mesmo mês
        assertEquals(DateUtils.getSomaDiasComDataEspecifica(dias1, data), dataEsperada1);
        // verifica soma em um mes
        assertEquals(DateUtils.getSomaDiasComDataEspecifica(dias2, data), dataEsperada2);
        // verifica soma em um ano
        assertEquals(DateUtils.getSomaDiasComDataEspecifica(dias3, data), dataEsperada3);

    }

    @Test
    public void verificarSubtracaoDiasComDataEspecifica(){
        String data = "01/09/2018";
        int dias1 = 6;
        int dias2 = 30;
        int dias3 = 365;
        String dataEsperada1 = "26/08/2018";
        String dataEsperada2 = "02/08/2018";
        String dataEsperada3 = "01/09/2017";

        // verifica subtracao dentro do mesmo mês
        assertEquals(dataEsperada1, DateUtils.getSubtracaoDiasComDataEspecifica(dias1, data));
        // verifica subtracao em um mes
        assertEquals(dataEsperada2, DateUtils.getSubtracaoDiasComDataEspecifica(dias2, data));
        // verifica subtracao em um ano
        assertEquals(dataEsperada3, DateUtils.getSubtracaoDiasComDataEspecifica(dias3, data));

    }

    @Test
    public void verificarIsDataFutura(){
        String dataAtual = DateUtils.getDataAtual();
        String dataFutura = DateUtils.getSomaDiasComDataEspecifica(5, dataAtual);
        String dataPassada = DateUtils.getSubtracaoDiasComDataEspecifica(5, dataAtual);

        // tem que retornar true
        boolean resultado1 = DateUtils.isDataFutura(dataFutura);
        assertTrue(resultado1);

        // tem que retornar false
        boolean resultado2 = DateUtils.isDataFutura(dataPassada);
        assertFalse(resultado2);

        // tem que retornar false
        boolean resultado3 = DateUtils.isDataFutura(dataAtual);
        assertFalse(resultado3);

    }

}
